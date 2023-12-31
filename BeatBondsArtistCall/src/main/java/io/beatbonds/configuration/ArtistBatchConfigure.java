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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import io.beatbonds.model.Artist;
import io.beatbonds.model.ArtistFromSpotify;
import io.beatbonds.service.ArtistGetDataService;

@Configuration
public class ArtistBatchConfigure {
	
	private JobBuilderFactory jobBuilderFactory;
	
	private StepBuilderFactory stepBuilderFactory;
	
	private JobCompletionListener jobCompletionListener;
	
	private DataSource dataSource;
	
	private JobLauncher jobLauncher;
	
	private JdbcTemplate jdbcTemplate;
	
	private ArtistGetDataService artistGetDataService;
	
	
	@Autowired
	public ArtistBatchConfigure(
			JobBuilderFactory jobBuilderFactory,
			StepBuilderFactory stepBuilderFactory, 
			DataSource dataSource,
			JobLauncher jobLauncher,
			JdbcTemplate jdbcTemplate,
			ArtistGetDataService artistGetDataService,
			JobCompletionListener jobCompletionListener
			) {
		this.jobBuilderFactory=jobBuilderFactory;
		this.stepBuilderFactory=stepBuilderFactory;
		this.dataSource=dataSource;
		this.jobLauncher=jobLauncher;
		this.jdbcTemplate=jdbcTemplate;
		this.artistGetDataService=artistGetDataService;
		this.jobCompletionListener=jobCompletionListener;
	}
	
//	fixedRate = 3 * 60 * 60 * 1000
//	fixedRate = 300000 (5 min)
//	fixedRate = 3 * 60 * 60 * 1000 (5 hour)
	@Scheduled(initialDelay = 0, fixedRate=300000)
	public void runJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, Exception {
		artistGetDataService.init();
		JobParametersBuilder paramBuilder = new JobParametersBuilder();
		paramBuilder.addDate("runTime", new Date());
		this.jobLauncher.run(job(), paramBuilder.toJobParameters());
	}

	@Bean
	public ItemReader<Artist> itemReader() throws Exception {
		return new ArtistItemReader(dataSource).itemReader();
	}
	
	@Bean
	public ItemWriter<ArtistFromSpotify> itemWriter(){
		return new ArtistItemWriter(dataSource).itemWriter();
	}
	
	@Bean
	public ItemProcessor<Artist, ArtistFromSpotify> trackedArtistItemProcessor() {
		return new ArtistItemProcessor(artistGetDataService); 
	}
	
	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(Integer.parseInt(System.getenv("CORE_POOL_SIZE")));
		executor.setMaxPoolSize(Integer.parseInt(System.getenv("MAX_CORE_POOL_SIZE")));
		return executor;
	}
	
	@Bean
	public Step chunkBasedStep() throws Exception {
		return this.stepBuilderFactory.get("chunkBasedStep")
				.<Artist, ArtistFromSpotify>chunk(Integer.parseInt(System.getenv("CHUNK_SIZE")))
				.reader(itemReader())
				.processor(trackedArtistItemProcessor())
				.writer(itemWriter())
				.taskExecutor(taskExecutor())
				.build();
	}
	
	/*
	 * @enhancement 
	 * Stop dropping table Just update the values instead of inserting new record again.
	 * It will save the ID of the artist which will be helpful for the pricing.
	 */
//	@Bean
//    public Step dropTableStep() {
//        return stepBuilderFactory.get("dropTableStep")
//                .tasklet((contribution, chunkContext) -> {
//                    jdbcTemplate.execute("DROP TABLE IF EXISTS beatbondsartist.artists_details");
//                    return null;
//                })
//                .build();
//    }
//	
//	@Bean
//    public Step createTableStep() {
//        return stepBuilderFactory.get("createTableStep")
//                .tasklet((contribution, chunkContext) -> {
//                    jdbcTemplate.execute("CREATE TABLE beatbondsartist.artists_details (\n"
//                    		+ "    artist_id INT AUTO_INCREMENT PRIMARY KEY,\n"
//                    		+ "    artist VARCHAR(255),\n"
//                    		+ "    popularity BIGINT,\n"
//                    		+ "    followers BIGINT,\n"
//                    		+ "    image VARCHAR(255),\n"
//                    		+ "    datetime DATETIME\n"
//                    		+ ")");
//                    return null;
//                })
//                .build();
//    }

	@Bean
	public Job job() throws Exception {
		return this.jobBuilderFactory.get("job")
				.start(chunkBasedStep())
//				.next(createTableStep())
//				.next(chunkBasedStep())
				.listener(jobCompletionListener)
				.build();
	}
	
//	@Bean
//	public Job job() throws Exception {
//		return this.jobBuilderFactory.get("job")
//				.start(dropTableStep())
//				.next(createTableStep())
//				.next(chunkBasedStep())
//				.listener(jobCompletionListener)
//				.build();
//	}
}
