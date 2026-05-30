package com.peachub.app.controller;

import com.peachub.app.dto.genius.GeniusAlbumDto;
import com.peachub.app.service.GeniusApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

@Controller
public class GeniusTestController {

    private final GeniusApiService geniusApiService;

    public GeniusTestController(
            GeniusApiService geniusApiService
    ) {
        this.geniusApiService = geniusApiService;
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {

        GeniusAlbumDto album =
                geniusApiService.searchAlbum(
                        "IGOR Tyler The Creator"
                );

        return album.toString();
    }
}