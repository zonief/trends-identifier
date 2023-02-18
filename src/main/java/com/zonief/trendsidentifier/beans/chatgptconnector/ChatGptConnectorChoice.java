package com.zonief.chatgptconnector.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Choice {

  private String text;
  private Integer index;
  @JsonProperty("finish_reason")
  private String finish_reason;

}
