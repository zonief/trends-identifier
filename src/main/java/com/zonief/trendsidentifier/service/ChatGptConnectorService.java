package com.zonief.trendsidentifier.service;

import com.zonief.trendsidentifier.beans.chatgptconnector.ChatGptConnectorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class ChatGptConnectorService {

  private final WebClient chatGptConnectorWebClient;

  public ChatGptConnectorService(@Qualifier("chatGptConnectorWebClient") WebClient chatGptConnectorWebClient) {
    this.chatGptConnectorWebClient = chatGptConnectorWebClient;
  }

  public String getNewsFromChatGpt(String message) {
    log.info("Sending message to chatGptConnector: {}", message);
    return chatGptConnectorWebClient.post()
        .uri("/api/v1/bot/send")
        .bodyValue(/*ChatGptConnectorRequest.builder().message(message).build()*/message)
        .retrieve()
        .bodyToMono(ChatGptConnectorResponse.class)
        .block().getChoices().get(0).getText();
  }

}
