package io.beatbonds.configuration;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import io.beatbonds.model.ArtistWithCalculatedPrice;

public class ArtistPricingItemWriter {
	
	private DataSource dataSource;
	
	private JdbcBatchItemWriterBuilder<ArtistWithCalculatedPrice> jdbcBatchItemWriter;
	
	@Autowired
	public ArtistPricingItemWriter(DataSource dataSource) {
		this.dataSource=dataSource;
	}
	
	
	public static String INSERT_ARTIST_PRICING_SQL = 
			"insert into beatbondsartist.artists_details_pricing(artist, popularity, followers, image, price) values(?,?,?,?,?)";
	
	public ItemWriter<ArtistWithCalculatedPrice> itemWriter() {
		this.jdbcBatchItemWriter 
			= new JdbcBatchItemWriterBuilder<ArtistWithCalculatedPrice>();
		return jdbcBatchItemWriter
			.dataSource(dataSource)
			.sql(INSERT_ARTIST_PRICING_SQL)
			.itemPreparedStatementSetter(new ArtistPricingItemPreparedStatementSetter())
			.build();
	}
}
