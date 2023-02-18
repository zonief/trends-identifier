package com.zonief.trendsidentifier;

import com.zonief.trendsidentifier.service.ChatGptConnectorService;
import com.zonief.trendsidentifier.service.GoogleTrendsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class TrendsIdentifierApplication implements CommandLineRunner{

  private final GoogleTrendsService googleTrendsService;
  private final ChatGptConnectorService chatGptConnectorService;

  public static void main(String[] args) {
    SpringApplication.run(TrendsIdentifierApplication.class, args);
  }

    @Override
    public void run(String... args) throws Exception {

    var trends = googleTrendsService.getTrendsAsList();
    var randomTrend = trends.get((int) (Math.random() * trends.size()));
    log.info("Random trend: {}\n{}", randomTrend, chatGptConnectorService.getNewsFromChatGpt(randomTrend));
    }

}
