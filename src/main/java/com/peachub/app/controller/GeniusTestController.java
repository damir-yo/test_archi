package com.peachub.app.controller;

import com.peachub.app.dto.genius.GeniusAlbumDto;
import com.peachub.app.service.GeniusApiService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class GeniusTestController {
    @Autowired
    private GeniusApiService geniusApiService;

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