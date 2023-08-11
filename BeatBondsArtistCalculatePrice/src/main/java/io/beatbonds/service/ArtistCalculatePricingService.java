package io.beatbonds.service;

import io.beatbonds.model.ArtistFromSpotifyDb;
import io.beatbonds.model.ArtistWithCalculatedPrice;

public interface ArtistCalculatePricingService {
	ArtistWithCalculatedPrice calculatePricing(ArtistFromSpotifyDb item);
}
