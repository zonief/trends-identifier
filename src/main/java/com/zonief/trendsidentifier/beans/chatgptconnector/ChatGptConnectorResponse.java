package com.zonief.trendsidentifier.beans.chatgptconnector;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptConnectorResponse implements Serializable{

  private String id;
  private String object;
  private LocalDate created;
  private String model;
  private List<ChatGptConnectorChoice> choices;

}
