package com.zonief.trendsidentifier.beans.chatgptconnector;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatGptConnectorRequest implements Serializable {

    private String message;
}
