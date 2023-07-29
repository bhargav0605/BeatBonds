package io.beatbonds.configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

@Component
public class TimeCalculation {
	
	private long startTime;
	
    @PostConstruct
    public void init() {
        startTime = System.currentTimeMillis();
        System.out.println("Initialization started at: " + startTime);
    }

    @PreDestroy
    public void cleanup() {
        long endTime = System.currentTimeMillis();
        System.out.println("Application is shutting down.");
        System.out.println("Total execution time: " + (endTime - startTime) + " milliseconds");
    }

}
