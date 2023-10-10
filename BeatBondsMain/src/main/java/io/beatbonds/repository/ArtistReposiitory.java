package io.beatbonds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.beatbonds.model.Artist;

@Repository
public interface ArtistReposiitory extends JpaRepository<Artist, Integer>{

}
