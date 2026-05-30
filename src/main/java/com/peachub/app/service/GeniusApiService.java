package com.peachub.app.service;

import com.peachub.app.dto.genius.GeniusAlbumDto;

public interface GeniusApiService {
    GeniusAlbumDto searchAlbum(String query);
}