package com.peachub.app.controller;

import com.peachub.app.service.MusicBrainzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MusicBrainzTestController {

    @Autowired
    private MusicBrainzService musicBrainzService;

    @GetMapping("/musicbrainz-test")
    @ResponseBody
    public String test() {

        return musicBrainzService
                .searchAlbums("IGOR")
                .toString();
    }
}