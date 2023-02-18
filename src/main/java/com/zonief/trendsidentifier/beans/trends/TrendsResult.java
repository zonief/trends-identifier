package com.zonief.trendsidentifier.beans.trends;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrendsResult {

  private String geo;
  private List<TrendingSearch> trendingSearches;

}
