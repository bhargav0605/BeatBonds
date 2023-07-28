package io.beatbonds.configuration;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import io.beatbonds.model.Artist;

public class ArtistFieldSetMapper implements FieldSetMapper<Artist> {

	@Override
	public Artist mapFieldSet(FieldSet fieldSet) throws BindException {
		Artist artist = new Artist();
		artist.setName(fieldSet.readString("Artists"));
		return artist;
	}

}
