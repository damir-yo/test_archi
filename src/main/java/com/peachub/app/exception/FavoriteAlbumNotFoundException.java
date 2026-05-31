package com.peachub.app.exception;

public class FavoriteAlbumNotFoundException
        extends RuntimeException {
    public FavoriteAlbumNotFoundException(Long albumId) {
        super("Album already added to favorites");
    }
}