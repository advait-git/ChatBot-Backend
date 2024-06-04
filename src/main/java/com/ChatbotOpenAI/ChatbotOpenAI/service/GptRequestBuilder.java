package com.ChatbotOpenAI.ChatbotOpenAI.service;

import com.ChatbotOpenAI.ChatbotOpenAI.model.ApiRequest;
import com.ChatbotOpenAI.ChatbotOpenAI.model.ChatGptRequest;
import com.ChatbotOpenAI.ChatbotOpenAI.model.ChatGptResponse;
import com.ChatbotOpenAI.ChatbotOpenAI.exceptions.NoResponseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class GptRequestBuilder {

    private final RestTemplate restTemplate;
    private final String url;
    private final String secret;

    public GptRequestBuilder(@Value("${ai.secret}") String secret, @Value("${ai.url}") String url) {
        this.url = url;
        this.secret = secret;
        this.restTemplate = new RestTemplate();
    }

    public ChatGptResponse executeRequest(ApiRequest request) throws RestClientException, NoResponseException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + secret);
        headers.set("Content-Type", "application/json");

        ChatGptRequest chatGptRequest = new ChatGptRequest(request);
        HttpEntity<ChatGptRequest> httpEntity = new HttpEntity<>(chatGptRequest, headers);

        ResponseEntity<ChatGptResponse> responseEntity;
        try {
            responseEntity = restTemplate.postForEntity(url, httpEntity, ChatGptResponse.class);
        } catch (RestClientException e) {
            throw new RestClientException("Error connecting to GPT API", e);
        }

        if (responseEntity.getBody() == null || responseEntity.getBody().getChoices().isEmpty()) {
            throw new NoResponseException();
        }

        return responseEntity.getBody();
    }
}
