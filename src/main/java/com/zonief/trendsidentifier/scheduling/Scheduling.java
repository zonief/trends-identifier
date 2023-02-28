package com.zonief.trendsidentifier.scheduling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zonief.trendsidentifier.beans.wordpress.Article;
import com.zonief.trendsidentifier.service.ChatGptConnectorService;
import com.zonief.trendsidentifier.service.GoogleTrendsService;
import com.zonief.trendsidentifier.service.KeyWordCheckerService;
import com.zonief.trendsidentifier.service.WordpressPublisherService;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class Scheduling {

  private final GoogleTrendsService googleTrendsService;
  private final ChatGptConnectorService chatGptConnectorService;
  private final KeyWordCheckerService keyWordCheckerService;
  private final WordpressPublisherService wordpressPublisherService;
  private final Random r = new Random();
  @Scheduled(fixedRate = /*60000*/10000)
  public void getArticle() throws JsonProcessingException {

    final var trends = googleTrendsService.getTrendsAsList();
    var article = getRandomizedArticle(trends);
    if(article != null){
      wordpressPublisherService.publish(article);
    }
  }

  @Nullable
  private Article getRandomizedArticle(List<String> trends) {
    for(var randomTrend : trends){
      if(keyWordCheckerService.isNew(randomTrend)){
        log.info("New trend identified: {}", randomTrend) ;
        String rawArticle = chatGptConnectorService.getNewsFromChatGpt(randomTrend);
        log.debug("Article: {}", rawArticle);
        return Article.builder()
            .title(StringUtils.split(rawArticle, "\n\n")[0])
            .content(StringUtils.substringAfter(rawArticle, StringUtils.split(rawArticle, "\n\n")[0]))
            .build();
      }
    }
    return null;
  }
}
