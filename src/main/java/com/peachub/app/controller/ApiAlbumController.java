package com.peachub.app.controller;

import com.peachub.app.dto.externalAlbum.ExternalAlbumDto;
import com.peachub.app.service.ExternalAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiAlbumController {

    @Autowired
    private ExternalAlbumService externalAlbumService;

    @GetMapping("/api/albums/search")
    public ExternalAlbumDto searchAlbum(
            @RequestParam String query
    ) {

        return externalAlbumService.searchAlbum(query);
    }
}