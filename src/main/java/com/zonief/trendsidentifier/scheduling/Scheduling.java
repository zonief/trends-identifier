package com.zonief.trendsidentifier.scheduling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zonief.trendsidentifier.config.KafkaTopicConfig;
import com.zonief.trendsidentifier.service.GoogleTrendsService;
import com.zonief.trendsidentifier.service.KeyWordCheckerService;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class Scheduling {

  private final GoogleTrendsService googleTrendsService;
  private final KeyWordCheckerService keyWordCheckerService;
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final KafkaTopicConfig kafkaTopicConfig;

  @Scheduled(fixedRate = 10000)
  public void getArticle() throws JsonProcessingException {
    final var trends = googleTrendsService.getTrendsAsList();
    final var trend = keyWordCheckerService.giveFirstValidKeyword(trends);
    if (StringUtils.isNotEmpty(trend)) {
      sendMessage(trend);
    }
  }

  public void sendMessage(String message) {
    CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(kafkaTopicConfig.getTopicName(), message);
    future.whenComplete((result, ex) -> {
      if (ex == null) {
        log.info("Sent message=[" + message +
            "] with offset=[" + result.getRecordMetadata().offset() + "]");
      } else {
        log.info("Unable to send message=[" +
            message + "] due to : " + ex.getMessage());
      }
    });
  }
}
