package com.ChatbotOpenAI.ChatbotOpenAI.exceptions;

public class NoResponseException extends CustomException{
    public NoResponseException(){
        super("No response received from the service.");
    }
}
