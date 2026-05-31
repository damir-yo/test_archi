package com.peachub.app.exception;

public class AccessDeniedException
        extends RuntimeException {
    public AccessDeniedException(String s) {
        super("Access denied");
    }
}
