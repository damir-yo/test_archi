package com.peachub.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "albums")
@Getter
@Setter
@NoArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String musicBrainzId;
    private String title;
    private String artist;
    private String coverUrl;
    private Integer releaseYear;
    private String genre;

    @OneToMany(mappedBy = "album")
    private List<Review> reviews;

    @OneToMany(mappedBy = "album")
    private List<FavoriteAlbum> favoritedBy;
}
