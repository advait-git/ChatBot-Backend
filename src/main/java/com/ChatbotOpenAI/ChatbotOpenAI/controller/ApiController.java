package com.ChatbotOpenAI.ChatbotOpenAI.controller;

import com.ChatbotOpenAI.ChatbotOpenAI.model.ApiRequest;
import com.ChatbotOpenAI.ChatbotOpenAI.model.ApiResponse;
import com.ChatbotOpenAI.ChatbotOpenAI.service.AiRequestProcessor;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor

public class ApiController {

  private final AiRequestProcessor aiRequestProcessor;

    @PostMapping(value = "/ai/request")
    public ResponseEntity<ApiResponse> get(@RequestBody ApiRequest request) {
        return ResponseEntity.ok(aiRequestProcessor.process(request));
    }
}
