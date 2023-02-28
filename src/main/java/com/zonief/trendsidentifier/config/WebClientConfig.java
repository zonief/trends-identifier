package com.zonief.trendsidentifier.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import javax.net.ssl.SSLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
@Slf4j
public class WebClientConfig {

  @Value("${webclient.chatGptConnector.port}")
  private String chatGptConnectorPort;
  @Value("${webclient.chatGptConnector.baseUrl}")
  private String chatGptConnectorBaseUrl;
  @Value("${webclient.wordpressPublisher.port}")
  private String wordpressPublisherPort;
  @Value("${webclient.wordpressPublisher.baseUrl}")
  private String wordpressPublisherBaseUrl;

  @Bean(name = "googleTrendsWebClient")
  @Primary
  public WebClient googleTrendsWebClient() {
    final int size = 16 * 1024 * 1024;
    final ExchangeStrategies strategies = ExchangeStrategies.builder()
        .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
        .build();
    return WebClient.builder()
        .defaultCookie("cookieKey", "cookieValue")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .baseUrl("https://trends.google.fr/")
        .exchangeStrategies(strategies)
        .build();
  }

  @Bean(name = "chatGptConnectorWebClient")
  public WebClient chatGptConnectorWebClient() throws SSLException {
    log.info("Creating chatGptConnectorWebClient with baseUrl: {} and port: {}", chatGptConnectorBaseUrl, chatGptConnectorPort);
    final int size = 16 * 1024 * 1024;
    final ExchangeStrategies strategies = ExchangeStrategies.builder()
        .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
        .build();
    SslContext sslContext = SslContextBuilder
        .forClient()
        .trustManager(InsecureTrustManagerFactory.INSTANCE)
        .build();
    var httpClient = HttpClient.create().wiretap(true).secure(t -> t.sslContext(sslContext));
    return WebClient.builder()
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.ALL_VALUE)
        .baseUrl("http://"+chatGptConnectorBaseUrl+":"+chatGptConnectorPort+"/")
        .exchangeStrategies(strategies)
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();
  }

  @Bean(name = "wordpressPublisherWebClient")
  public WebClient wordpressPublisherWebClient() throws SSLException {
    log.info("Creating wordpressPublisherWebClient with baseUrl: {} and port: {}", wordpressPublisherBaseUrl, wordpressPublisherPort);
    final int size = 16 * 1024 * 1024;
    final ExchangeStrategies strategies = ExchangeStrategies.builder()
        .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
        .build();
    SslContext sslContext = SslContextBuilder
        .forClient()
        .trustManager(InsecureTrustManagerFactory.INSTANCE)
        .build();
    var httpClient = HttpClient.create().wiretap(true).secure(t -> t.sslContext(sslContext));
    return WebClient.builder()
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.ALL_VALUE)
        .baseUrl("http://"+wordpressPublisherBaseUrl+":"+wordpressPublisherPort+"/")
        .exchangeStrategies(strategies)
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();
  }

}
