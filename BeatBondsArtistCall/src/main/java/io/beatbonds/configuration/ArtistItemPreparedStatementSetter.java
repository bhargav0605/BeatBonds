package io.beatbonds.configuration;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import io.beatbonds.model.Artist;

public class ArtistItemPreparedStatementSetter implements ItemPreparedStatementSetter<Artist> {

	@Override
	public void setValues(Artist item, PreparedStatement ps) throws SQLException {
//		ps.setInt(1, item.getId());
		ps.setString(1, item.getName());
	}

}
