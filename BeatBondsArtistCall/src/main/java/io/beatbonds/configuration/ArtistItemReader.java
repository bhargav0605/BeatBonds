package io.beatbonds.configuration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
public class ArtistItemReader implements ItemReader<String> {
	
	private List<String> dataSet = new ArrayList<>();
	
	public ArtistItemReader() {
		this.dataSet.add("1");
		this.dataSet.add("2");
		this.dataSet.add("3");
		this.dataSet.add("4");
		this.dataSet.add("5");
		this.iterator = this.dataSet.iterator();	
	}
	
	private Iterator<String> iterator;

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		return iterator.hasNext() ? iterator.next() : null;
	}
}
