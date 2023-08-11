package io.beatbonds.configuration;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.beatbonds.model.ArtistWithCalculatedPrice;

//@Component
public class ArtistDbItemWriter {
//	public class ArtistDbItemWriter implements ItemWriter<ArtistPricedDb>{
	private DataSource dataSource;
	
	private JdbcBatchItemWriterBuilder<ArtistWithCalculatedPrice> jdbcBatchItemWriter;
	
	@Autowired
	public ArtistDbItemWriter(DataSource dataSource) {
		this.dataSource=dataSource;
	}
	
	
	public static String INSERT_ARTIST_PRICING_SQL = 
			"insert into beatbondsartist.artists_details_pricing(artist, popularity, followers, image, price) values(?,?,?,?,?)";
	
//	@Override
//	public void write(List<? extends ArtistPricedDb> items) throws Exception {
//		items.stream().forEach(item->System.out.println(item.toString()));
//	}
	
	public ItemWriter<ArtistWithCalculatedPrice> itemWriter() {
		this.jdbcBatchItemWriter 
			= new JdbcBatchItemWriterBuilder<ArtistWithCalculatedPrice>();
		return jdbcBatchItemWriter
			.dataSource(dataSource)
			.sql(INSERT_ARTIST_PRICING_SQL)
			.itemPreparedStatementSetter(new ArtistDbItemPreparedStatementSetter())
			.build();
	}
}
