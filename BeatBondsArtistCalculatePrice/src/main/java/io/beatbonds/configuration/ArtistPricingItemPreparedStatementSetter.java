package io.beatbonds.configuration;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import io.beatbonds.model.ArtistWithCalculatedPrice;

public class ArtistPricingItemPreparedStatementSetter implements ItemPreparedStatementSetter<ArtistWithCalculatedPrice>{

	@Override
	public void setValues(ArtistWithCalculatedPrice item, PreparedStatement ps) throws SQLException {
		ps.setString(1, item.getName());
		ps.setLong(2, item.getPopularity());
		ps.setLong(3, item.getFollowers());
		ps.setString(4, item.getImage());
		ps.setLong(5, item.getPrice());
		
	}

}
