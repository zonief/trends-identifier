package com.zonief.trendsidentifier.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zonief.trendsidentifier.beans.trends.TrendingSearch;
import com.zonief.trendsidentifier.beans.trends.TrendsResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class GoogleTrendsService {
  private final WebClient googleTrendsWebClient;
  private final ObjectMapper objectMapper;

  public GoogleTrendsService(@Qualifier("googleTrendsWebClient") WebClient googleTrendsWebClient, @Autowired ObjectMapper objectMapper) {
    this.googleTrendsWebClient = googleTrendsWebClient;
    this.objectMapper = objectMapper;
  }

  private TrendsResponse getTrends() throws JsonProcessingException {
    log.info("Getting trends from google...");
    return objectMapper.readValue(StringUtils.substringAfter(googleTrendsWebClient.get()
        .uri("/trends/api/topdailytrends?geo=FR")
        .retrieve()
        .bodyToMono(String.class).block(), ")]}',"), TrendsResponse.class);
  }

  public List<String> getTrendsAsList() throws JsonProcessingException {

    var resultList = getTrends().getTrendsResult().getTrendingSearches().stream()
        .map(TrendingSearch::getTitle).toList();

    resultList.forEach(log::info);

    return resultList;
  }

}
