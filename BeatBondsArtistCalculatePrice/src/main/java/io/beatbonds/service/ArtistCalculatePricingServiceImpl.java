package io.beatbonds.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.beatbonds.model.ArtistFromSpotifyDb;
import io.beatbonds.model.ArtistWithCalculatedPrice;

@Service
public class ArtistCalculatePricingServiceImpl implements ArtistCalculatePricingService {
	
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(ArtistCalculatePricingServiceImpl.class);

	@Override
	public ArtistWithCalculatedPrice calculatePricing(ArtistFromSpotifyDb item) {
		ArtistWithCalculatedPrice artistPricedDb = new ArtistWithCalculatedPrice();
		artistPricedDb.setName(item.getName());
		artistPricedDb.setPopularity(item.getPopularity());
		artistPricedDb.setFollowers(item.getFollowers());
		artistPricedDb.setImage(item.getImage());
		artistPricedDb.setPrice(calculatePrice(item.getPopularity(), item.getFollowers()));
		LOGGER.info(artistPricedDb.toString());
		return artistPricedDb;
	}
	
	private Long calculatePrice(Long popularity, Long followers) {
//		double variablility;
//		double noisy_popularity;
//		
//		if(popularity<57) {
//			
//		}
//		
//		BigDecimal pricing = new BigDecimal(0.0);
//		pricing.add(0.0);
		return popularity+followers;
	}

}
