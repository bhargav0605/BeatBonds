package io.beatbonds.configuration;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import io.beatbonds.model.Artist;

@Component
public class ArtistItemReader implements ItemStreamReader<Artist> {
	
	public static String[] tokens = new String[] {"Artists"};
	
	private FlatFileItemReader<Artist> itemReader;
	
	public ArtistItemReader() {
		this.itemReader = new FlatFileItemReader<Artist>();
		this.itemReader.setResource(new FileSystemResource("/home/bhargav/Java-Projects/mstock/src/main/resources/data/artists.csv"));
		
		DefaultLineMapper<Artist> lineMapper = new DefaultLineMapper<Artist>();
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames(tokens);
		
		lineMapper.setLineTokenizer(tokenizer);
		
		lineMapper.setFieldSetMapper(new ArtistFieldSetMapper());
		
		itemReader.setLineMapper(lineMapper);

	}

	@Override
	public Artist read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		return itemReader.read();
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		itemReader.open(executionContext);
		
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		itemReader.update(executionContext);
		
	}

	@Override
	public void close() throws ItemStreamException {
		itemReader.close();
		
	}
	

}
