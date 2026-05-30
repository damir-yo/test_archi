package com.peachub.app.controller;

import com.peachub.app.dto.musicbrainz.MusicBrainzAlbumDto;
import com.peachub.app.service.MusicBrainzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicBrainzRestController {

    @Autowired
    private MusicBrainzService musicBrainzService;

    @GetMapping("/api/musicbrainz/search")
    public MusicBrainzAlbumDto search(
            @RequestParam String query
    ) {
        return musicBrainzService.searchAlbum(query);
    }
}