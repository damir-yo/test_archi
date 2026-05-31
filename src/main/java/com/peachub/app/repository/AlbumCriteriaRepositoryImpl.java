package com.peachub.app.repository;

import com.peachub.app.entity.Album;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AlbumCriteriaRepositoryImpl implements AlbumCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Album> searchAlbumsCriteria(String title, String artist, String genre) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Album> query = cb.createQuery(Album.class);
        Root<Album> album = query.from(Album.class);
        List<Predicate> predicates = new ArrayList<>();

        if (title != null && !title.isBlank()) {

            predicates.add(cb.like(cb.lower(album.get("title")), "%" + title.toLowerCase() + "%"));
        }

        if (artist != null && !artist.isBlank()) {
            predicates.add(cb.like(cb.lower(album.get("artist")), "%" + artist.toLowerCase() + "%"));
        }

        if (genre != null && !genre.isBlank()) {
            predicates.add(cb.like(cb.lower(album.get("genre")), "%" + genre.toLowerCase() + "%"));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
}