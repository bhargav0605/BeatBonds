package io.beatbonds.configuration;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import io.beatbonds.model.ArtistDb;

@Component
public class ArtistDbItemWriter implements ItemWriter<String>{

	@Override
	public void write(List<? extends String> items) throws Exception {
		items.stream().forEach(item->System.out.println(item));
//		for (ArtistDb item : items) {
//            System.out.println(item.toString());
//        }
//		System.out.println(items.stream());
	}
}
