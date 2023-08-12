package io.beatbonds.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.beatbonds.model.Artist;
import io.beatbonds.model.ArtistFromSpotify;

@Service
public class ArtistGetDataServiceImpl implements ArtistGetDataService {
	
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(ArtistGetDataServiceImpl.class);
	
	private long startTime;
	
	private String token;
	private int tokenTime;

	@Override
	public ArtistFromSpotify getArtistSpotifyData(Artist item) {

		ArtistFromSpotify artstDb = new ArtistFromSpotify();
		
		// Need environment variable
		String apiUrl = "https://api.spotify.com/v1/search";
		
		//replaceAll
		String q = item.getName().contains(" ")?item.getName().replace(" ", "%20") : item.getName(); 

		String type = "artist";
		String limit = "1";
		String offset = "0";
		
		Long popularity=0L;
		String nameArt;
		Long followers=0l;
		String image="";
		
		apiUrl += "?q="+q+"&type="+type+"&limit="+limit+"&offset="+offset;
		
		try {        	
            URL url = new URL(apiUrl);
            
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
//            conn.setRequestProperty("Authorization", "Bearer "+sharedData.getSharedToken());
            conn.setRequestProperty("Authorization", "Bearer "+token);
            conn.setDoOutput(true);
            
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    JSONObject jsonResponse = new JSONObject(response.toString());

                    JSONObject itemsArr = new JSONObject();
                    JSONObject imageArr = new JSONObject();
                    itemsArr = (JSONObject) jsonResponse.getJSONObject("artists").getJSONArray("items").get(0);
                    imageArr = (JSONObject)itemsArr.getJSONArray("images").get(0);
                    image = imageArr.getString("url");
                    
                    LOGGER.info("##########################################");

                    popularity=itemsArr.getLong("popularity");
                    followers=itemsArr.getJSONObject("followers").getLong("total");
                    nameArt=itemsArr.getString("name");
                    image = imageArr.getString("url");

                    LOGGER.info(popularity+" "+followers+" "+nameArt+" "+image);
              
                }
            } else {
                LOGGER.info("\"POST request failed with response code: \" + responseCode");
            }

            conn.disconnect();
            
		} catch (Exception e) {
			e.getMessage();
		}
		
		artstDb.setName(item.getName());
        artstDb.setPopularity(popularity);
        artstDb.setFollowers(followers);
        artstDb.setImage(image);

		return artstDb;
	}
	
	@PostConstruct
    public void init() {
		LOGGER.info("Initialization started at: " + startTime);
        
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
                    
                 // Parse the JSON response
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    
                    // Access different parameters from the JSON response
                    String accessToken = jsonResponse.getString("access_token");
                    int expiresIn = jsonResponse.getInt("expires_in");
                    String tokenType = jsonResponse.getString("token_type");
                    
                    token = accessToken;
//                    sharedData.setSharedToken(accessToken);
                    System.out.println("Access Token: " + token);
                    tokenTime = expiresIn;
                    System.out.println("Expires In: " + tokenTime);
                    System.out.println("Token Type: " + tokenType);
              
                }
            } else {
                LOGGER.info("POST request failed with response code: " + responseCode);
            }

            conn.disconnect();
            
		} catch (Exception e) {
			e.getMessage();
		}
    }
}
