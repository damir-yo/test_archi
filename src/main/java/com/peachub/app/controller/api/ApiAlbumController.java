package com.peachub.app.controller.api;

import com.peachub.app.dto.album.AlbumRatingDto;
import com.peachub.app.dto.album.AlbumResponseDto;
import com.peachub.app.dto.external.ExternalAlbumDto;
import com.peachub.app.mapper.AlbumMapper;
import com.peachub.app.service.AlbumService;
import com.peachub.app.service.ExternalAlbumService;
import com.peachub.app.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Albums API", description = "Работа с альбомами")
@RestController
@AllArgsConstructor
public class ApiAlbumController {
    @Autowired
    private ExternalAlbumService externalAlbumService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private AlbumMapper albumMapper;
    @Autowired
    private ReviewService reviewService;

    @Operation(summary = "получить топ альбомов")
    @GetMapping("/api/albums/top")
    public List<AlbumRatingDto> getTopAlbums() {
        return reviewService.getTopAlbums();
    }
    @Operation(summary = "Поиск альбомов через внешний API")
    @GetMapping("/api/albums/search")
    public List<ExternalAlbumDto> searchAlbum(@RequestParam String query) {
        return externalAlbumService.searchAlbums(query);
    }
    @Operation(summary = "Получить альбом по id", description = "Возвращает информацию об альбоме из локальной базы данных")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Альбом найден"),
            @ApiResponse(responseCode = "404", description = "Альбом не найден")
    })
    @GetMapping("/api/albums/{id}")
    public AlbumResponseDto getAlbum(@Parameter(description = "ID альбома") @PathVariable Long id) {
        return albumMapper.toDto(albumService.getAlbumById(id));
    }

    @Operation(summary = "Получить все альбомы из локальной базы")
    @GetMapping("/api/albums")
    public List<AlbumResponseDto> getAllAlbums() {
        return albumService.getAllAlbums().stream().map(albumMapper::toDto).toList();
    }

    @Operation(summary = "Поиск альбомов в локальной базе")
    @GetMapping("/api/albums/local-search")
    public List<AlbumResponseDto> searchLocalAlbums(@RequestParam String query) {
        return albumService.search(query).stream().map(albumMapper::toDto).toList();
    }
}