package com.ChatbotOpenAI.ChatbotOpenAI.exceptions;

public class NegativeResponseException extends CustomException{
    public NegativeResponseException(){
        super("Received a negative response.");
    }
}
