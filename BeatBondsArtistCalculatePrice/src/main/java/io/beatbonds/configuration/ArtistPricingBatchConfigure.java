package io.beatbonds.configuration;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
