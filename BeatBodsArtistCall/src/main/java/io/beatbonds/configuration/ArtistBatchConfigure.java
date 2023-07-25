package io.beatbonds.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArtistBatchConfigure {
	
	private JobBuilderFactory jobBuilderFactory;
	
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	public ArtistBatchConfigure(JobBuilderFactory jobBuilderFactory,
			StepBuilderFactory stepBuilderFactory) {
		this.jobBuilderFactory=jobBuilderFactory;
		this.stepBuilderFactory=stepBuilderFactory;
	}
	
	@Bean
	public ItemReader<String> itemReader() {
		return new ArtistItemReader(); 
	}
	
	@Bean
	public ItemWriter<String> itemWriter(){
		return new ArtistItemWriter();
	}
	
	@Bean
	public Step chunkBasedStep() {
		return this.stepBuilderFactory.get("chunkBasedStep")
				.<String, String>chunk(3)
				.reader(itemReader())
				.writer(itemWriter()).build();
	}
	
	@Bean
	public Job job() {
		return this.jobBuilderFactory.get("job")
				.start(chunkBasedStep())
				.build();
	}
	

}
