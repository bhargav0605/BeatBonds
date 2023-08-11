package io.beatbonds.service;

import org.springframework.stereotype.Service;

import io.beatbonds.model.ArtistFromSpotifyDb;
import io.beatbonds.model.ArtistWithCalculatedPrice;

@Service
public class ArtistCalculatePricingServiceImpl implements ArtistCalculatePricingService {

	@Override
	public ArtistWithCalculatedPrice calculatePricing(ArtistFromSpotifyDb item) {
		ArtistWithCalculatedPrice artistPricedDb = new ArtistWithCalculatedPrice();
		artistPricedDb.setName(item.getName());
		artistPricedDb.setPopularity(item.getPopularity());
		artistPricedDb.setFollowers(item.getFollowers());
		artistPricedDb.setImage(item.getImage());
		artistPricedDb.setPrice(item.getPopularity()+1000);
		return artistPricedDb;
	}

}
