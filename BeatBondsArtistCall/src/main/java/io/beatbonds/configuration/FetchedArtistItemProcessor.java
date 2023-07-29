package io.beatbonds.configuration;

import org.springframework.batch.item.ItemProcessor;

import io.beatbonds.model.Artist;

public class FetchedArtistItemProcessor implements ItemProcessor<Artist, Artist> {

	@Override
	public Artist process(Artist item) throws Exception {
		Artist artst = new Artist();
		System.out.println(item);
		artst.setName(String.valueOf(item.getName().length()));
		return artst;
	}

}
