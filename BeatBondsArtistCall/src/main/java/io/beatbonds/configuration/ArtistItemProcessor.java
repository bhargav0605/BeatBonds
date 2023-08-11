package io.beatbonds.configuration;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import io.beatbonds.model.Artist;
import io.beatbonds.model.ArtistFromSpotify;
import io.beatbonds.service.ArtistGetDataService;

public class ArtistItemProcessor implements ItemProcessor<Artist, ArtistFromSpotify> {
	
	private ArtistGetDataService artistGetDataService;
		
	@Autowired
	public ArtistItemProcessor(ArtistGetDataService artistGetDataServiceImpl) {
		this.artistGetDataService=artistGetDataServiceImpl;
	}

	@Override
	public ArtistFromSpotify process(Artist item) throws Exception {
		return artistGetDataService.getArtistSpotifyData(item);
	}

}
