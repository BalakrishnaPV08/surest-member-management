package com.tietoevry.surest.member.management.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) { super(message); }
    public static class NotFound extends ApiException { public NotFound(String m){super(m);} }
    public static class Conflict extends ApiException { public Conflict(String m){super(m);} }
    public static class Unauthorized extends ApiException { public Unauthorized(String m){super(m);} }
}

