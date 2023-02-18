package com.zonief.chatgptconnector.beans;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptResponse {

  private String id;
  private String object;
  private LocalDate created;
  private String model;
  private List<Choice> choices;

}
