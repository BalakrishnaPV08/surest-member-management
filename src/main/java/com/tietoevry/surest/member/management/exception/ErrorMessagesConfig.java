package com.tietoevry.surest.member.management.exception;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "error")
public class ErrorMessagesConfig {

    private Map<String, Map<String, ErrorDetail>> messages = new HashMap<>();

    public Map<String, Map<String, ErrorDetail>> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, Map<String, ErrorDetail>> messages) {
        this.messages = messages;
    }

    public ErrorDetail getErrorDetail(String category, String key) {
        return messages.getOrDefault(category, new HashMap<>())
                .getOrDefault(key, new ErrorDetail(500, "An error occurred", "Internal server error"));
    }

    public static class ErrorDetail {
        private int statusCode;
        private String userMessage;
        private String systemMessage;

        public ErrorDetail() {}

        public ErrorDetail(int statusCode, String userMessage, String systemMessage) {
            this.statusCode = statusCode;
            this.userMessage = userMessage;
            this.systemMessage = systemMessage;
        }

        public int getStatusCode() { return statusCode; }
        public String getUserMessage() { return userMessage; }
        public String getSystemMessage() { return systemMessage; }

        public void setStatusCode(int statusCode) { this.statusCode = statusCode; }
        public void setUserMessage(String userMessage) { this.userMessage = userMessage; }
        public void setSystemMessage(String systemMessage) { this.systemMessage = systemMessage; }
    }
}
