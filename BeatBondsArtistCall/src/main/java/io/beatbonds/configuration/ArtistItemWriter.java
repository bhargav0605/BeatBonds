package io.beatbonds.configuration;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class ArtistItemWriter implements ItemWriter<String>{

	@Override
	public void write(List<? extends String> items) throws Exception {
		System.out.println(String.format("Received list of size: %s", items.size()));
		items.forEach(System.out::println);
		
	}

}
