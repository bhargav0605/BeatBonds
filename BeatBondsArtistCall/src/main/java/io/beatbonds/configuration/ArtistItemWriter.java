package io.beatbonds.configuration;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import io.beatbonds.model.ArtistDb;

public class ArtistItemWriter {
	
private DataSource dataSource;
	
	private JdbcBatchItemWriterBuilder<ArtistDb> jdbcBatchItemWriterBuilder;
	
	public static String INSERT_ARTIST_SQL = 
			"insert into beatbondsartist.artists_details(artist, popularity, followers, image) values(?,?,?,?)";
	
	@Autowired
	public ArtistItemWriter(DataSource dataSource) {
		this.dataSource=dataSource;
	}
	
	public ItemWriter<ArtistDb> itemWriter(){
		this.jdbcBatchItemWriterBuilder = new JdbcBatchItemWriterBuilder<ArtistDb>();
		return jdbcBatchItemWriterBuilder
		.dataSource(dataSource)
		.sql(INSERT_ARTIST_SQL)
		.itemPreparedStatementSetter(new ArtistItemPreparedStatementSetter())
		.build();
	}

}
