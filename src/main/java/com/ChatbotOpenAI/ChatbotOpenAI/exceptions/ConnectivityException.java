package com.ChatbotOpenAI.ChatbotOpenAI.exceptions;

public class ConnectivityException extends CustomException{
    public ConnectivityException(){
        super("Failed to connect to the service.");
    }
}
