package io.beatbonds.configuration;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import io.beatbonds.model.ArtistFromSpotifyDb;
import io.beatbonds.model.ArtistWithCalculatedPrice;
import io.beatbonds.service.ArtistCalculatePricingService;

public class ArtistPricingItemProcessor implements ItemProcessor<ArtistFromSpotifyDb, ArtistWithCalculatedPrice>{
	
	private ArtistCalculatePricingService artistCalculatePricingService;
	
	@Autowired
	public ArtistPricingItemProcessor(ArtistCalculatePricingService artistCalculatePricingService) {
		this.artistCalculatePricingService=artistCalculatePricingService;
	}
	
	
	@Override
	public ArtistWithCalculatedPrice process(ArtistFromSpotifyDb item) throws Exception {
		return artistCalculatePricingService.calculatePricing(item);
	}

}
