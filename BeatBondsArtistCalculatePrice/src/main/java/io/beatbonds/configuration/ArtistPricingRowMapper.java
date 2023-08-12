package io.beatbonds.configuration;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import io.beatbonds.model.ArtistFromSpotifyDb;

public class ArtistPricingRowMapper implements RowMapper<ArtistFromSpotifyDb>{

	@Override
	public ArtistFromSpotifyDb mapRow(ResultSet rs, int rowNum) throws SQLException {
		ArtistFromSpotifyDb artistFromSpotifyDb = new ArtistFromSpotifyDb();
		artistFromSpotifyDb.setName(rs.getString("artist"));
		artistFromSpotifyDb.setPopularity(rs.getLong("popularity"));
		artistFromSpotifyDb.setFollowers(rs.getLong("followers"));
		artistFromSpotifyDb.setImage(rs.getString("image"));
		return artistFromSpotifyDb;
	}

}
