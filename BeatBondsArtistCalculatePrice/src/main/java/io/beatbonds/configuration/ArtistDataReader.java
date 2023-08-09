package io.beatbonds.configuration;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;

public class ArtistDataReader{
	
	private JdbcPagingItemReaderBuilder<String> jdbcPagingItemReader;
	
	public ItemReader<String> itemReader(){
		this.jdbcPagingItemReader = new JdbcPagingItemReaderBuilder<String>();
		return jdbcPagingItemReader.dataSource(null)
			.name(null)
			.queryProvider(null)
			.rowMapper(null)
			.pageSize(0)
			.build();
	}

}
