package com.zonief.trendsidentifier.beans.chatgptconnector;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptConnectorChoice implements Serializable {

  private String text;
  private Integer index;
  @JsonProperty("finish_reason")
  private String finishReason;

}
