package io.beatbonds.configuration;

import java.util.Date;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import io.beatbonds.model.Artist;
import io.beatbonds.model.ArtistDb;
import io.beatbonds.shared.SharedData;

@Configuration
public class ArtistBatchConfigure {
	
//	public static String[] tokens = new String[] {"Artists"};
	
	private JobBuilderFactory jobBuilderFactory;
	
	private StepBuilderFactory stepBuilderFactory;
	
	private DataSource dataSource;
	
	private JobLauncher jobLauncher;
	
	private SharedData sharedData;
	
	
	public static String ARTIST_SQL = "select artist "
			+ "from beatbondsartist.artists order by id";
	
//	public static String INSERT_ARTIST_SQL = "insert into "
//			+ "beatbondsartist.artists2(artist)"
//			+ " values(?)";
	
	public static String INSERT_ARTIST_SQL = "insert into "
			+ "beatbondsartist.artists_details(artist, popularity, followers, image)"
			+ " values(?,?,?,?)";
	
	@Scheduled(initialDelay = 0, fixedRate = 300000)
	public void runJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, Exception {
		JobParametersBuilder paramBuilder = new JobParametersBuilder();
		paramBuilder.addDate("runTime", new Date());
		this.jobLauncher.run(job(), paramBuilder.toJobParameters());
	}
	
//	 JobLauncher jobLauncher,
	@Autowired//
	public ArtistBatchConfigure(
			JobBuilderFactory jobBuilderFactory,
			StepBuilderFactory stepBuilderFactory, 
			DataSource dataSource,
			JobLauncher jobLauncher,
			SharedData sharedData
			) {
		this.jobBuilderFactory=jobBuilderFactory;
		this.stepBuilderFactory=stepBuilderFactory;
		this.dataSource=dataSource;
		this.jobLauncher=jobLauncher;
		this.sharedData=sharedData;
	}
	
	@Bean
	public ItemReader<Artist> itemReader() throws Exception {
		return new JdbcPagingItemReaderBuilder<Artist>()
				.dataSource(dataSource)
				.name("jdbcCursorItemReader")
				.queryProvider(queryProvider())
				.rowMapper(new ArtistRowMapper())
				.pageSize(10)
				.build();
	}
	
	@Bean
	public ItemWriter<ArtistDb> itemWriter(){
//		System.out.println("writer: "+sharedData.getSharedToken());
		return new JdbcBatchItemWriterBuilder<ArtistDb>()
				.dataSource(dataSource)
				.sql(INSERT_ARTIST_SQL)
				.itemPreparedStatementSetter(new ArtistItemPreparedStatementSetter())
				.build();
	}
	
	@Bean
	public ItemProcessor<Artist, ArtistDb> trackedArtistItemProcessor() {
		return new FetchedArtistItemProcessor(); 
	}
	
	
	@Bean
	public PagingQueryProvider queryProvider() throws Exception {
		SqlPagingQueryProviderFactoryBean factory = new SqlPagingQueryProviderFactoryBean();
		factory.setSelectClause("select id, artist");
		factory.setFromClause("from beatbondsartist.artists");
		factory.setSortKey("id");
		factory.setDataSource(dataSource);
		return factory.getObject();
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
		return this.stepBuilderFactory.get("chunkBasedStep")
				.<Artist, ArtistDb>chunk(10)
				.reader(itemReader())
				.processor(trackedArtistItemProcessor())
				.writer(itemWriter())
				.taskExecutor(taskExecutor())
				.build();
	}

	@Bean
	public Job job() throws Exception {
		return this.jobBuilderFactory.get("job")
				.start(chunkBasedStep())
				.build();
	}
}
