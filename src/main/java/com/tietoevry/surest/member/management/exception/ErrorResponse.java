package com.tietoevry.surest.member.management.exception;

public class ErrorResponse {
    public String code;
    public String systemMessage;
    public String userMessage;

    public ErrorResponse(String code, String systemMessage, String userMessage) {
        this.code = code;
        this.systemMessage = systemMessage;
        this.userMessage = userMessage;
    }

    public ErrorResponse() {}
}
