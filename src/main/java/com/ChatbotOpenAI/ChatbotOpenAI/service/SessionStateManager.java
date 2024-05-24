package com.ChatbotOpenAI.ChatbotOpenAI.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionStateManager {

    private final RedisTemplate<String,String> redisTemplate;
    private final Set<String> activeSessions = ConcurrentHashMap.newKeySet();
    private final int MAX_SESSIONS = 10;


    public SessionStateManager(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public synchronized void addSession(String sessionId, String state) {
        if (activeSessions.size() >= MAX_SESSIONS) {
            throw new RuntimeException("Maximum number of concurrent sessions reached.");
        }
        activeSessions.add(sessionId);
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(sessionId, state);
    }
    public synchronized void removeSession(String sessionId) {
        activeSessions.remove(sessionId);
        redisTemplate.delete(sessionId);
    }
    public String getSessionState(String sessionId) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(sessionId);
    }

    public void updateSessionState(String sessionId, String state) {
        if (activeSessions.contains(sessionId)) {
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            ops.set(sessionId, state);
        }
    }

}
