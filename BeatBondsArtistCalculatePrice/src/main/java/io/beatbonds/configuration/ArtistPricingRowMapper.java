package io.beatbonds.configuration;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import io.beatbonds.model.ArtistFromSpotifyDb;

public class ArtistPricingRowMapper implements RowMapper<ArtistFromSpotifyDb>{

	@Override
	public ArtistFromSpotifyDb mapRow(ResultSet rs, int rowNum) throws SQLException {
		ArtistFromSpotifyDb artistDb = new ArtistFromSpotifyDb();
		artistDb.setName(rs.getString("artist"));
		artistDb.setPopularity(rs.getLong("popularity"));
		artistDb.setFollowers(rs.getLong("followers"));
		artistDb.setImage(rs.getString("image"));
		return artistDb;
	}

}
