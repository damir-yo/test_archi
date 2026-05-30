package com.peachub.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class CoverArtArchiveService {

    @Autowired
    @Qualifier("coverArtRestClient")
    private RestClient restClient;

    public String getCoverUrl(String releaseGroupId) {

        String url = "/release-group/"
                + releaseGroupId
                + "/front";

        try {

            restClient.head()
                    .uri(url)
                    .retrieve()
                    .toBodilessEntity();

            return "https://coverartarchive.org" + url;

        } catch (Exception e) {

            return null;
        }
    }
}