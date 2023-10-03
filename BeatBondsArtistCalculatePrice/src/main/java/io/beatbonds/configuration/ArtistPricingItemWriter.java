package io.beatbonds.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import io.beatbonds.model.ArtistWithCalculatedPrice;

public class ArtistPricingItemWriter {
	
	private DataSource dataSource;
	
	@Autowired
	public ArtistPricingItemWriter(DataSource dataSource) {
		this.dataSource=dataSource;
	}
	
//	private final static String INSERT_ARTIST_PRICING_SQL = 
//	"insert into beatbondsartist.artists_details_pricing(artist, popularity, followers, image, price, datetime) values(?,?,?,?,?,?)";
	
	private final static String INSERT_UPDATE_PRICING_SQL = "INSERT INTO beatbondsartist.artists_details_pricing(artist, popularity, followers, image, price, datetime)\n"
			+ "VALUES (?,?,?,?,?,?)\n"
			+ "ON DUPLICATE KEY UPDATE popularity = VALUES(popularity), followers = VALUES(followers), price = VALUES(price), datetime = VALUES(datetime);";
	
	private final static String INSERT_ARTIST_PRICING_ANALYSIS_SQL = 
	"insert into beatbondsartist.artists_details_pricing_analysis(artist, popularity, followers, image, price, datetime) values(?,?,?,?,?,?)";

	public ItemWriter<ArtistWithCalculatedPrice> itemWriter() {
        List<ItemWriter<? super ArtistWithCalculatedPrice>> writers = new ArrayList<>();

        ItemWriter<ArtistWithCalculatedPrice> insertWriter = new JdbcBatchItemWriterBuilder<ArtistWithCalculatedPrice>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//                .sql(INSERT_ARTIST_PRICING_SQL)
                .sql(INSERT_UPDATE_PRICING_SQL)
                .itemPreparedStatementSetter(new ArtistPricingItemPreparedStatementSetter())
                .dataSource(dataSource)
                .build();

        ItemWriter<ArtistWithCalculatedPrice> updateWriter = new JdbcBatchItemWriterBuilder<ArtistWithCalculatedPrice>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql(INSERT_ARTIST_PRICING_ANALYSIS_SQL)
                .itemPreparedStatementSetter(new ArtistPricingItemPreparedStatementSetter())
                .dataSource(dataSource)
                .build();

        writers.add(insertWriter);
        writers.add(updateWriter);

        CompositeItemWriter<ArtistWithCalculatedPrice> compositeItemWriter = new CompositeItemWriter<>();
        compositeItemWriter.setDelegates(writers);

        return compositeItemWriter;
    }
}
