package com.ChatbotOpenAI.ChatbotOpenAI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptRequest {

    private String model;
    private List<ChatGptRequestMessage> messages;

    public ChatGptRequest(ApiRequest request) {
        this.model = "gpt-3.5-turbo-16k-0613";
        this.messages = List.of(new ChatGptRequestMessage(request));
    }
}