package io.beatbonds.configuration;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.beatbonds.shared.SharedData;

@Component
public class TimeCalculation {
	
	private long startTime;
	
	private String token;
	private int tokenTime;
	private SharedData sharedData;
	
	@Autowired
	public TimeCalculation(SharedData sharedData) {
		this.sharedData=sharedData;
	}
	
	
    @PostConstruct
    public void init() {
        startTime = System.currentTimeMillis();
        System.out.println("Initialization started at: " + startTime);
        
        // Token generation call 
        String apiUrl = "https://accounts.spotify.com/api/token";
        String spotifyId = System.getenv("SPOTIFY_CLINT_ID");
        String spotifyClientSecret = System.getenv("SPOTIFY_CLIENT_SECRET");
        
        // call api
        try {
        	String authHeaderValue = "Basic " + Base64.getEncoder().encodeToString((spotifyId + ":" + spotifyClientSecret).getBytes(StandardCharsets.UTF_8));
        	
        	// Set up the connection
            URL url = new URL(apiUrl);
            
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", authHeaderValue);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);


            String requestBody = "grant_type=client_credentials";
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write(requestBody.getBytes(StandardCharsets.UTF_8));
            }
            
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    System.out.println("Response: " + response.toString());
                    
                 // Parse the JSON response
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    
                    // Access different parameters from the JSON response
                    String accessToken = jsonResponse.getString("access_token");
                    int expiresIn = jsonResponse.getInt("expires_in");
                    String tokenType = jsonResponse.getString("token_type");
                    
                 // Do something with the retrieved parameters
                    token = accessToken;
                    sharedData.setSharedToken(accessToken);
                    System.out.println("Access Token: " + token);
                    tokenTime = expiresIn;
                    System.out.println("Expires In: " + tokenTime);
                    System.out.println("Token Type: " + tokenType);
              
                }
            } else {
                System.out.println("POST request failed with response code: " + responseCode);
            }

            conn.disconnect();
            
		} catch (Exception e) {
			e.getMessage();
		}
    }

    @PreDestroy
    public void cleanup() {
        long endTime = System.currentTimeMillis();
        System.out.println("Application is shutting down.");
        System.out.println("Total execution time: " + (endTime - startTime)/1000 + " seconds");
    }

}
