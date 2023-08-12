package io.beatbonds.configuration;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import io.beatbonds.model.Artist;

public class ArtistItemReader {
	
	private DataSource dataSource;
	
	@Autowired
	public ArtistItemReader(DataSource dataSource) {
		this.dataSource=dataSource;
	}
	
	private JdbcPagingItemReaderBuilder<Artist> jdbcPagingItemReader;
	
	public ItemReader<Artist> itemReader() throws Exception {
		this.jdbcPagingItemReader = new JdbcPagingItemReaderBuilder<Artist>();
		return jdbcPagingItemReader
				.dataSource(dataSource)
				.name("jdbcCursorItemReader")
				.queryProvider(queryProvider())
				.rowMapper(new ArtistRowMapper())
				.pageSize(10)
				.build();
	}
	
	public PagingQueryProvider queryProvider() throws Exception {
		SqlPagingQueryProviderFactoryBean factory = new SqlPagingQueryProviderFactoryBean();
		factory.setSelectClause("select id, artist");
		factory.setFromClause("from beatbondsartist.artists");
		factory.setSortKey("id");
		factory.setDataSource(dataSource);
		return factory.getObject();
	}
}
