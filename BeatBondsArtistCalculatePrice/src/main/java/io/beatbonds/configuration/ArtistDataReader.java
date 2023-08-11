package io.beatbonds.configuration;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import io.beatbonds.model.ArtistFromSpotifyDb;

public class ArtistDataReader {
	private DataSource dataSource;
	
	@Autowired
	public ArtistDataReader(DataSource dataSource) {
		this.dataSource=dataSource;
	}
	
	private JdbcPagingItemReaderBuilder<ArtistFromSpotifyDb> jdbcPagingItemReader;
	
	public ItemReader<ArtistFromSpotifyDb> itemReader() throws Exception{
		this.jdbcPagingItemReader = new JdbcPagingItemReaderBuilder<ArtistFromSpotifyDb>();
		return jdbcPagingItemReader.dataSource(dataSource)
			.name("jdbcCursorItemReaderPricing")
			.queryProvider(queryProvider())
			.rowMapper(new ArtistDbRowMapper())
			.pageSize(10)
			.build();
	}
	
	public PagingQueryProvider queryProvider() throws Exception {
		SqlPagingQueryProviderFactoryBean factory = new SqlPagingQueryProviderFactoryBean();
		// Need column names
		factory.setSelectClause("select artist_id, artist, popularity, followers, image");
		factory.setFromClause("from beatbondsartist.artists_details");
		factory.setSortKey("artist_id");
		factory.setDataSource(dataSource);
		return factory.getObject();
	}
}
