package io.beatbonds.configuration;

import org.springframework.batch.item.ItemProcessor;

import io.beatbonds.model.ArtistDb;
import io.beatbonds.model.ArtistPricedDb;

//@Component
public class ArtistDbItemProcessor implements ItemProcessor<ArtistDb, ArtistPricedDb>{

	@Override
	public ArtistPricedDb process(ArtistDb item) throws Exception {
		ArtistPricedDb artistPricedDb = new ArtistPricedDb();
		artistPricedDb.setName(item.getName());
		artistPricedDb.setPopularity(item.getPopularity());
		artistPricedDb.setFollowers(item.getFollowers());
		artistPricedDb.setImage(item.getImage());
		artistPricedDb.setPrice(item.getPopularity()+1000);
		return artistPricedDb;
	}

}
