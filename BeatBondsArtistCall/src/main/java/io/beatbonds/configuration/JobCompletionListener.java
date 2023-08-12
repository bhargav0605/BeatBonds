package io.beatbonds.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class JobCompletionListener extends JobExecutionListenerSupport{
	
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(JobCompletionListener.class);
	
    private RestTemplate restTemplate;
	
    @Autowired
	public JobCompletionListener(RestTemplate restTemplate) {
		this.restTemplate=restTemplate;
	}
	
	@Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
        	try {
        		ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                        System.getenv("PRICING_SERVICE_URL"), 
                        null, 
                        String.class
                    );
            	LOGGER.info(responseEntity.getBody());
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
        	
        } else {
        	LOGGER.error("Something is wrong, job seems not completed.");
        }
    }
}
