package io.beatbonds.configuration;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import io.beatbonds.model.Artist;

public class ArtistRowMapper implements RowMapper<Artist> {

	@Override
	public Artist mapRow(ResultSet rs, int rowNum) throws SQLException {
		Artist artist = new Artist();
		artist.setName(rs.getString("artist"));
		return artist;
	}

}
