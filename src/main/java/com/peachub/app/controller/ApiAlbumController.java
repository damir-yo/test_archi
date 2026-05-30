package com.peachub.app.controller;

import com.peachub.app.dto.externalAlbum.ExternalAlbumDto;
import com.peachub.app.dto.musicbrainz.MusicBrainzAlbumDto;
import com.peachub.app.service.ExternalAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiAlbumController {

    @Autowired
    private ExternalAlbumService externalAlbumService;

    @GetMapping("/api/albums/search")
    public List<ExternalAlbumDto> searchAlbum(
            @RequestParam String query
    ) {
        return externalAlbumService.searchAlbums(query);
    }
}