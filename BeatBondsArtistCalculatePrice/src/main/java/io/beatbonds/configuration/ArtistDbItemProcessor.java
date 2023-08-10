package io.beatbonds.configuration;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import io.beatbonds.model.ArtistDb;

//@Component
public class ArtistDbItemProcessor implements ItemProcessor<ArtistDb, String>{

	@Override
	public String process(ArtistDb item) throws Exception {
		String str = Long.toString(item.getPopularity()+10000);
		return str;
	}

}
