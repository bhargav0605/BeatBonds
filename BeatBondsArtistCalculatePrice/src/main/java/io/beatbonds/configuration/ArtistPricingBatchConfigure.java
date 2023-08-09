package io.beatbonds.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ArtistPricingBatchConfigure {
	
	private JobBuilderFactory jobBuilderFactory;
	
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	public ArtistPricingBatchConfigure(JobBuilderFactory jobBuilderFactory,
				StepBuilderFactory stepBuilderFactory) {
		this.jobBuilderFactory=jobBuilderFactory;
		this.stepBuilderFactory=stepBuilderFactory;
	}
	// Create ItemWiter, ItemReader, ItemProcessor
	
	@Bean
	public ItemReader<String> itemReader(){
		// needs configuration with datasource
		return new ArtistDataReader().itemReader();
	}
	
	@Bean
	public ItemWriter<String> itemWriter(){
		return null;
	}
	
	@Bean
	public ItemProcessor<String, String> itemProcessor(){
		return null;
	}
	
	// Ended writer and processor and reader
	@Bean
	public TaskExecutor taskExecutor() {
		// configuration of pool size and thread size
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(6);
		executor.setMaxPoolSize(20);
		return executor;
	}
	
	@Bean
	public Step chunkBasedStep() {
		// configure reader, writer, processor, task executor(multithreading)
		return null;
	}
	
	@Bean
	public Job job() {
		
		// Configure all the steps one after another if multiple
		return null;
	}

}
