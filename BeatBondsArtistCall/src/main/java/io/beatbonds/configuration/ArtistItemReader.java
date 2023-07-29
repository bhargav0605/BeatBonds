package io.beatbonds.configuration;


import javax.sql.DataSource;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import io.beatbonds.model.Artist;

//@Component
public class ArtistItemReader extends JdbcCursorItemReader<Artist> {
	
//	private DataSource dataSource;
//	
//	public static String ARTIST_SQL = "select artist "
//			+ "from beatbondsartist.artists order by id";
//	
//	@Autowired
//	public ArtistItemReader(DataSource dataSource) {
//		this.dataSource=dataSource;
//		this.setSql(ARTIST_SQL);
//		this.setRowMapper(new ArtistRowMapper());
//	}

	
	
	

}
