package com.zonief.trendsidentifier.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WordpressPublisherService {
/*
  private final WebClient wordpressPublisherWebClient;

  public WordpressPublisherService(@Qualifier("wordpressPublisherWebClient") WebClient wordpressPublisherWebClient) {
    this.wordpressPublisherWebClient = wordpressPublisherWebClient;
  }

  public void publish(Article article) {
    wordpressPublisherWebClient.post()
        .uri("/api/v1/publisher/publish")
        .bodyValue(article)
        .retrieve()
        .bodyToMono(Void.class)
        .block();
    log.info("Article {} sent to publisher-service.", article.getTitle());
  }*/

}
