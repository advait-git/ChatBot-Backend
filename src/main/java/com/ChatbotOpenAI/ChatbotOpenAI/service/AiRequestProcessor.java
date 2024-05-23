package com.ChatbotOpenAI.ChatbotOpenAI.service;

import com.ChatbotOpenAI.ChatbotOpenAI.model.ApiRequest;
import com.ChatbotOpenAI.ChatbotOpenAI.model.ApiResponse;
import com.ChatbotOpenAI.ChatbotOpenAI.model.ChatGptResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class AiRequestProcessor {

    private final GptRequestBuilder gpt;

    public ApiResponse process(ApiRequest request){
        log.info("### -> Incoming request: {}" , request.getMessage());
        ChatGptResponse response = gpt.executeRequest(request);
        log.info("### -> ChatGPT response: {}", response);
        return new ApiResponse(response.getChoices().get(0).getMessage().getContent());
    }
}
