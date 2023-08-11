package io.beatbonds.configuration;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import io.beatbonds.model.Artist;
import io.beatbonds.model.ArtistFromSpotify;

public class ArtistItemPreparedStatementSetter implements ItemPreparedStatementSetter<ArtistFromSpotify> {

	@Override
	public void setValues(ArtistFromSpotify item, PreparedStatement ps) throws SQLException {
//		ps.setInt(1, item.getId());
		ps.setString(1, item.getName());
		ps.setLong(2, item.getPopularity());
		ps.setLong(3, item.getFollowers());
		ps.setString(4, item.getImage());
	}

}
