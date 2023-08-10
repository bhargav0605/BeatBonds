package io.beatbonds.configuration;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import io.beatbonds.model.ArtistDb;

public class ArtistDbRowMapper implements RowMapper<ArtistDb>{

	@Override
	public ArtistDb mapRow(ResultSet rs, int rowNum) throws SQLException {
		ArtistDb artistDb = new ArtistDb();
		artistDb.setName(rs.getString("artist"));
		artistDb.setPopularity(rs.getLong("popularity"));
		artistDb.setFollowers(rs.getLong("followers"));
		artistDb.setImage(rs.getString("image"));
		return artistDb;
	}

}
