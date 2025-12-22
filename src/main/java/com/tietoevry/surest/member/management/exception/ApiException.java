package com.tietoevry.surest.member.management.exception;

public class ApiException extends RuntimeException {

    private final String category;
    private final String key;

    protected ApiException(String category, String key) {
        super(category + "." + key);
        this.category = category;
        this.key = key;
    }

    public String getCategory() {
        return category;
    }

    public String getKey() {
        return key;
    }

    // -------- Subclasses --------

    public static class NotFound extends ApiException {
        public NotFound(String category, String key) {
            super(category, key);
        }
    }

    public static class Conflict extends ApiException {
        public Conflict(String category, String key) {
            super(category, key);
        }
    }

    public static class Unauthorized extends ApiException {
        public Unauthorized(String category, String key) {
            super(category, key);
        }
    }
}
