package io.beatbonds.configuration;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import io.beatbonds.model.ArtistFromSpotifyDb;
import io.beatbonds.model.ArtistWithCalculatedPrice;
import io.beatbonds.service.ArtistCalculatePricingService;

@Configuration
public class ArtistPricingBatchConfigure {
	
	private JobBuilderFactory jobBuilderFactory;
	
	private StepBuilderFactory stepBuilderFactory;
	
	private DataSource dataSource;
	
	private ArtistCalculatePricingService artistCalculatePricingService;
	
	@Autowired
	public ArtistPricingBatchConfigure(JobBuilderFactory jobBuilderFactory,
				StepBuilderFactory stepBuilderFactory,
				DataSource dataSource,
				JobLauncher jobLauncher,
				JdbcTemplate jdbcTemplate,
				ArtistCalculatePricingService artistCalculatePricingService) {
		this.jobBuilderFactory=jobBuilderFactory;
		this.stepBuilderFactory=stepBuilderFactory;
		this.dataSource=dataSource;
		this.artistCalculatePricingService=artistCalculatePricingService;
	}
	
	@Bean
	public ItemReader<ArtistFromSpotifyDb> itemReader() throws Exception{
		return new ArtistPricingItemReader(dataSource).itemReader();
	}
	
	@Bean
	public ItemWriter<ArtistWithCalculatedPrice> itemWriter(){
		return new ArtistPricingItemWriter(dataSource).itemWriter();
	}
	
	@Bean
	public ItemProcessor<ArtistFromSpotifyDb, ArtistWithCalculatedPrice> itemProcessor(){
		return new ArtistPricingItemProcessor(artistCalculatePricingService);
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
		return this.stepBuilderFactory.get("artistDbChunkBasedStep")
				.<ArtistFromSpotifyDb, ArtistWithCalculatedPrice>chunk(Integer.parseInt(System.getenv("CHUNK_SIZE")))
				.reader(itemReader())
				.processor(itemProcessor())
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
//                    jdbcTemplate.execute("DROP TABLE IF EXISTS beatbondsartist.artists_details_pricing");
//                    return null;
//                })
//                .build();
//    }
	
//	@Bean
//    public Step createTableStep() {
//        return stepBuilderFactory.get("createTableStep")
//                .tasklet((contribution, chunkContext) -> {
//                    jdbcTemplate.execute("CREATE TABLE beatbondsartist.artists_details_pricing (\n"
//                    		+ "    artist_id INT AUTO_INCREMENT PRIMARY KEY,\n"
//                    		+ "    artist VARCHAR(255),\n"
//                    		+ "    popularity BIGINT,\n"
//                    		+ "    followers BIGINT,\n"
//                    		+ "    image VARCHAR(255),\n"
//                    		+"     price DECIMAL(10, 2),\n"
//                    		+"     datetime DATETIME\n"
//                    		+ ")");
//                    return null;
//                })
//                .build();
//    }
	
//	@Bean
//	public Job job() throws Exception {
//		return this.jobBuilderFactory.get("artistDbJob")
//				.start(dropTableStep())
//				.next(createTableStep())
//				.next(chunkBasedStep())
//				.build();
//	}
	
	@Bean
	public Job job() throws Exception {
		return this.jobBuilderFactory.get("artistDbJob")
				.start(chunkBasedStep())
				.build();
	}
}
