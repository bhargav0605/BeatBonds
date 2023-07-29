package io.beatbonds.configuration;

import org.springframework.batch.item.ItemProcessor;

import io.beatbonds.model.Artist;

public class FetchedArtistItemProcessor implements ItemProcessor<Artist, Artist> {

	@Override
	public Artist process(Artist item) throws Exception {
		Artist artst = new Artist();
		artst.setName(item.getName()+" K");
		return artst;
	}

}
