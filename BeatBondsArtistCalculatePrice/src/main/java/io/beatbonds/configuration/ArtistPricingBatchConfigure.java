package io.beatbonds.configuration;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import io.beatbonds.model.ArtistDb;

@Configuration
public class ArtistPricingBatchConfigure {
	
	private JobBuilderFactory jobBuilderFactory;
	
	private StepBuilderFactory stepBuilderFactory;
	
	private DataSource dataSource;
	
	@Autowired
	public ArtistPricingBatchConfigure(JobBuilderFactory jobBuilderFactory,
				StepBuilderFactory stepBuilderFactory,
				DataSource dataSource) {
		this.jobBuilderFactory=jobBuilderFactory;
		this.stepBuilderFactory=stepBuilderFactory;
		this.dataSource=dataSource;
	}
	
	@Bean
	public PagingQueryProvider queryProvider() throws Exception {
		SqlPagingQueryProviderFactoryBean factory = new SqlPagingQueryProviderFactoryBean();
		factory.setSelectClause("select artist_id, artist, popularity, followers, image");
		factory.setFromClause("from beatbondsartist.artists_details");
		factory.setSortKey("artist_id");
		factory.setDataSource(dataSource);
		return factory.getObject();
	}
	
	@Bean
	public ItemReader<ArtistDb> itemReader() throws Exception{
		return new ArtistDataReader(dataSource).itemReader();
	}
	
	@Bean
	public ItemWriter<String> itemWriter(){
		return new ArtistDbItemWriter();
	}
	
	@Bean
	public ItemProcessor<ArtistDb, String> itemProcessor(){
		return new ArtistDbItemProcessor();
	}
	

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(6);
		executor.setMaxPoolSize(20);
		return executor;
	}
	
	@Bean
	public Step chunkBasedStep() throws Exception {
		return this.stepBuilderFactory.get("artistDbChunkBasedStep")
				.<ArtistDb, String>chunk(10)
				.reader(itemReader())
				.processor(itemProcessor())
				.writer(itemWriter())
				.taskExecutor(taskExecutor())
				.build();
	}
	
	@Bean
	public Job job() throws Exception {
		return this.jobBuilderFactory.get("artistDbJob")
				.start(chunkBasedStep())
				.build();
	}
}
