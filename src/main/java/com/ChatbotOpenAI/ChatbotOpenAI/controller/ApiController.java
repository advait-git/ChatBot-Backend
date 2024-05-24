package com.ChatbotOpenAI.ChatbotOpenAI.controller;

import com.ChatbotOpenAI.ChatbotOpenAI.model.ApiRequest;
import com.ChatbotOpenAI.ChatbotOpenAI.model.ApiResponse;
import com.ChatbotOpenAI.ChatbotOpenAI.service.AiRequestProcessor;
import com.ChatbotOpenAI.ChatbotOpenAI.service.SessionStateManager;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
@AllArgsConstructor
public class ApiController {

    private final AiRequestProcessor aiProcessor;
    private final SessionStateManager sessionStateManager;

    @PostMapping(value = "/ai/request")
    public ResponseEntity<ApiResponse> get(@RequestBody ApiRequest request, HttpSession session) {
        String sessionId = session.getId();
        String sessionState = sessionStateManager.getSessionState(sessionId);

        if (sessionState == null) {
            sessionStateManager.addSession(sessionId, "initial state");
            sessionState = "initial state";
        }

        ApiResponse response = aiProcessor.process(request, sessionState);
        sessionStateManager.updateSessionState(sessionId, response.getMessage());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/ai/session")
    public ResponseEntity<String> endSession(HttpSession session) {
        sessionStateManager.removeSession(session.getId());
        session.invalidate();
        return ResponseEntity.ok("Session ended");
    }
}
