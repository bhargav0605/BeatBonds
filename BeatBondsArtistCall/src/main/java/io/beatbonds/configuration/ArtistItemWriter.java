package io.beatbonds.configuration;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import io.beatbonds.model.ArtistFromSpotify;

public class ArtistItemWriter {

	private DataSource dataSource;
	
	@Autowired
//	private JdbcTemplate jdbcTemplate;

	private JdbcBatchItemWriterBuilder<ArtistFromSpotify> jdbcBatchItemWriterBuilder;

	/*
	 * If artist present in database then just update the values other wise add at
	 * the end.
	 * 
	 * 1. Check if record is present or not.
	 * 2. If present then just update it so return "UPDATE" Item writer.
	 * 3. else do "INSERT" query.
	 */
//	private final static String INSERT_ARTIST_SQL = "insert into beatbondsartist.artists_details(artist, popularity, followers, image, datetime) values(?,?,?,?,?)";
	
	private final static String INSERT_UPDATE_ARTIST_SQL = "INSERT INTO beatbondsartist.artists_details(artist, popularity, followers, image, datetime)\n"
			+ "VALUES (?,?,?,?,?)\n"
			+ "ON DUPLICATE KEY UPDATE popularity = VALUES(popularity), followers = VALUES(followers), datetime = VALUES(datetime);";
	
	@Autowired
	public ArtistItemWriter(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public ItemWriter<ArtistFromSpotify> itemWriter() {
		
//		String sql = "SELECT * FROM beatbondsartist.artists_details WHERE artist=";
		
		this.jdbcBatchItemWriterBuilder = new JdbcBatchItemWriterBuilder<ArtistFromSpotify>();
		return jdbcBatchItemWriterBuilder
				.dataSource(dataSource)
				.sql(INSERT_UPDATE_ARTIST_SQL)
//				.sql(INSERT_ARTIST_SQL)
				.itemPreparedStatementSetter(new ArtistItemPreparedStatementSetter())
				.build();
	}

}
