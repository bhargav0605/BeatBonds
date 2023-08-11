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
import io.beatbonds.service.ArtistGetDataServiceImpl;

@Configuration
public class ArtistBatchConfigure {
	
	private JobBuilderFactory jobBuilderFactory;
	
	private StepBuilderFactory stepBuilderFactory;
	
	private DataSource dataSource;
	
	private JobLauncher jobLauncher;
	
	private JdbcTemplate jdbcTemplate;
	
	private ArtistGetDataService artistGetDataService;
	
	
	public static String ARTIST_SQL = "select artist from beatbondsartist.artists order by id";
	
	
	public static String INSERT_ARTIST_SQL = 
			"insert into beatbondsartist.artists_details(artist, popularity, followers, image) values(?,?,?,?)";
	
	@Autowired
	public ArtistBatchConfigure(
			JobBuilderFactory jobBuilderFactory,
			StepBuilderFactory stepBuilderFactory, 
			DataSource dataSource,
			JobLauncher jobLauncher,
			JdbcTemplate jdbcTemplate,
			ArtistGetDataServiceImpl artistGetDataServiceImpl
			) {
		this.jobBuilderFactory=jobBuilderFactory;
		this.stepBuilderFactory=stepBuilderFactory;
		this.dataSource=dataSource;
		this.jobLauncher=jobLauncher;
		this.jdbcTemplate=jdbcTemplate;
		this.artistGetDataService=artistGetDataServiceImpl;
	}
	
//	fixedRate = 3 * 60 * 60 * 1000
//	fixedRate = 300000 (5 min)
	@Scheduled(initialDelay = 0, fixedRate = 3 * 60 * 60 * 1000)
	public void runJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, Exception {
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
		executor.setCorePoolSize(6);
		executor.setMaxPoolSize(20);
		return executor;
	}
	
	@Bean
	public Step chunkBasedStep() throws Exception {
		return this.stepBuilderFactory.get("chunkBasedStep")
				.<Artist, ArtistFromSpotify>chunk(10)
				.reader(itemReader())
				.processor(trackedArtistItemProcessor())
				.writer(itemWriter())
				.taskExecutor(taskExecutor())
				.build();
	}
	
	@Bean
    public Step dropTableStep() {
        return stepBuilderFactory.get("dropTableStep")
                .tasklet((contribution, chunkContext) -> {
                    jdbcTemplate.execute("DROP TABLE IF EXISTS beatbondsartist.artists_details");
                    return null;
                })
                .build();
    }
	
	@Bean
    public Step createTableStep() {
        return stepBuilderFactory.get("createTableStep")
                .tasklet((contribution, chunkContext) -> {
                    jdbcTemplate.execute("CREATE TABLE beatbondsartist.artists_details (\n"
                    		+ "    artist_id INT AUTO_INCREMENT PRIMARY KEY,\n"
                    		+ "    artist VARCHAR(255),\n"
                    		+ "    popularity BIGINT,\n"
                    		+ "    followers BIGINT,\n"
                    		+ "    image VARCHAR(255)\n"
                    		+ ")");
                    return null;
                })
                .build();
    }

	@Bean
	public Job job() throws Exception {
		return this.jobBuilderFactory.get("job")
				.start(dropTableStep())
				.next(createTableStep())
				.next(chunkBasedStep())
				.build();
	}
}
