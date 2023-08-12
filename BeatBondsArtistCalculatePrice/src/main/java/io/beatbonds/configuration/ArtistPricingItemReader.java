package io.beatbonds.configuration;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import io.beatbonds.model.ArtistFromSpotifyDb;

public class ArtistPricingItemReader {
	private DataSource dataSource;
	
	@Autowired
	public ArtistPricingItemReader(DataSource dataSource) {
		this.dataSource=dataSource;
	}
	
	private JdbcPagingItemReaderBuilder<ArtistFromSpotifyDb> jdbcPagingItemReader;
	
	public ItemReader<ArtistFromSpotifyDb> itemReader() throws Exception{
		this.jdbcPagingItemReader = new JdbcPagingItemReaderBuilder<ArtistFromSpotifyDb>();
		return jdbcPagingItemReader.dataSource(dataSource)
			.name("jdbcCursorItemReaderPricing")
			.queryProvider(queryProvider())
			.rowMapper(new ArtistPricingRowMapper())
			.pageSize(10)
			.build();
	}
	
	public PagingQueryProvider queryProvider() throws Exception {
		SqlPagingQueryProviderFactoryBean factory = new SqlPagingQueryProviderFactoryBean();
		factory.setSelectClause("select artist_id, artist, popularity, followers, image");
		factory.setFromClause("from beatbondsartist.artists_details");
		factory.setSortKey("artist_id");
		factory.setDataSource(dataSource);
		return factory.getObject();
	}
}
