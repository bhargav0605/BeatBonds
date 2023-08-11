package io.beatbonds.service;

import io.beatbonds.model.Artist;
import io.beatbonds.model.ArtistFromSpotify;

public interface ArtistGetDataService {
	
	ArtistFromSpotify getArtistSpotifyData(Artist item);

}
