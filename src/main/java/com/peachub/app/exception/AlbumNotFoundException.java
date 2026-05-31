package com.peachub.app.exception;

public class AlbumNotFoundException extends RuntimeException {
    public AlbumNotFoundException(Long id) {
        super("Album not found: " + id);
    }
}
