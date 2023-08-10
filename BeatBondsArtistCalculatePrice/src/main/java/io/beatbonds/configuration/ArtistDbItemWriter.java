package io.beatbonds.configuration;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import io.beatbonds.model.ArtistPricedDb;

@Component
public class ArtistDbItemWriter implements ItemWriter<ArtistPricedDb>{
	
	// need database writer, right now just printing
	@Override
	public void write(List<? extends ArtistPricedDb> items) throws Exception {
		items.stream().forEach(item->System.out.println(item.getPrice()));
	}
}
