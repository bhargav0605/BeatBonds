package io.beatbonds.controller;

import java.util.Date;

import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.beatbonds.configuration.ArtistPricingBatchConfigure;

@RestController
public class ArtistpricingItemController {
	
	private JobLauncher jobLauncher;
	
	private ArtistPricingBatchConfigure artistPricingBatchConfigure;
	
	@Autowired
	public ArtistpricingItemController(
			JobLauncher jobLauncher,
			ArtistPricingBatchConfigure artistPricingBatchConfigure) {
		this.jobLauncher=jobLauncher;
		this.artistPricingBatchConfigure=artistPricingBatchConfigure;
	}
	
	@PostMapping("/start-job-b")
    public ResponseEntity<String> calculatePrice() {
        try {
        	JobParametersBuilder paramBuilder = new JobParametersBuilder();
        	paramBuilder.addDate("runTime", new Date());
        	this.jobLauncher.run(artistPricingBatchConfigure.job(), paramBuilder.toJobParameters());
            return ResponseEntity.ok("Calculate Price job started");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Calculate Pricing Job failed to start");
        }
    }

}
