package com.zonief.trendsidentifier.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrendsResponse {

  @JsonProperty("default")
  private TrendsResult trendsResult;

}
