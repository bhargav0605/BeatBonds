package io.beatbonds.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.beatbonds.model.Artist;
import io.beatbonds.repository.ArtistReposiitory;

@Service
public class ArtistServiceImpl implements ArtistService {
	
	private ArtistReposiitory artistRepository;
	
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(ArtistServiceImpl.class);
	
	public ArtistServiceImpl(ArtistReposiitory artistRepository) {
		this.artistRepository=artistRepository;
	}

	@Override
	public List<Artist> fetchAllArtists() {
		List<Artist> listOfAllArtists = artistRepository.findAll();
		LOGGER.info("Here are your artists: "+listOfAllArtists);
		return listOfAllArtists;
	}

}
