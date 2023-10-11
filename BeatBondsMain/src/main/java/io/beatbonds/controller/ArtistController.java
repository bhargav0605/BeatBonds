package io.beatbonds.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.beatbonds.exceptions.ArtistNotFoundException;
import io.beatbonds.model.Artist;
import io.beatbonds.service.ArtistService;

@RestController
@RequestMapping("/api/v1")
public class ArtistController {
	
	private ArtistService artistService;
	
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(ArtistController.class);
	
	public ArtistController(ArtistService artistService) {
		this.artistService=artistService;
	}
	
	@GetMapping("/artists")
	public ResponseEntity<List<Artist>> getAllArtist() throws ArtistNotFoundException{
		List<Artist> listOfAllArtist = null;
		listOfAllArtist = artistService.fetchAllArtists();

		if(listOfAllArtist == null) {
			throw new ArtistNotFoundException("Empty");
		} else {
			return new ResponseEntity<>(listOfAllArtist, HttpStatus.OK);
		}
	}
}
