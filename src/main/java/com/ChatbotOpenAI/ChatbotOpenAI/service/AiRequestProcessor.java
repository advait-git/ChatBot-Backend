package com.ChatbotOpenAI.ChatbotOpenAI.service;

import com.ChatbotOpenAI.ChatbotOpenAI.exceptions.ConnectivityException;
import com.ChatbotOpenAI.ChatbotOpenAI.exceptions.CustomException;
import com.ChatbotOpenAI.ChatbotOpenAI.exceptions.NegativeResponseException;
import com.ChatbotOpenAI.ChatbotOpenAI.exceptions.NoResponseException;
import com.ChatbotOpenAI.ChatbotOpenAI.model.ApiRequest;
import com.ChatbotOpenAI.ChatbotOpenAI.model.ApiResponse;
import com.ChatbotOpenAI.ChatbotOpenAI.model.ChatGptResponse;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
@Log4j2
@AllArgsConstructor
public class AiRequestProcessor {

    private final GptRequestBuilder gpt;

    public ApiResponse process(ApiRequest request, String sessionState) {
        log.info("### -> Incoming request: {}", request.getMessage());

        if (request.getMessage() == null || request.getMessage().trim().isEmpty()) {
            return new ApiResponse("Message content cannot be null or empty.");
        }

        try {
            ChatGptResponse response = gpt.executeRequest(request);
            if (response == null || response.getChoices().isEmpty()) {
                throw new NoResponseException();
            }

            String reply = response.getChoices().get(0).getMessage().getContent();

            log.info("### -> ChatGPT response: {}", response);
            return new ApiResponse(reply);

        } catch (RestClientException e) {
            log.error("### -> Connectivity issue: ", e);
            throw new ConnectivityException();
        } catch (NoResponseException e) {
            log.error("### -> No response received: ", e);
            throw e;
        } catch (Exception e) {
            log.error("### -> Unexpected error: ", e);
            throw new CustomException("An unexpected error occurred.");
        }
    }
}
