package io.beatbonds.configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import io.beatbonds.model.Artist;
import io.beatbonds.shared.SharedData;

public class FetchedArtistItemProcessor implements ItemProcessor<Artist, Artist> {
	
	@Autowired
	private SharedData sharedData;
	
    private static final Logger logger = LoggerFactory.getLogger(FetchedArtistItemProcessor.class);


	@Override
	public Artist process(Artist item) throws Exception {
		Artist artst = new Artist();
		String apiUrl = "https://api.spotify.com/v1/search";
		
		String q = item.getName().contains(" ")?item.getName().replaceAll(" ", "%20") : item.getName(); 
		
		String type = "artist";
		String limit = "1";
		String offset = "0";
		
		Long popularity=0L;
		
		apiUrl += "?q="+q+"&type="+type+"&limit="+limit+"&offset="+offset;

		
		
        try {        	
            URL url = new URL(apiUrl);
            
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer "+sharedData.getSharedToken());
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

                    JSONObject something = new JSONObject();
                    something = (JSONObject) jsonResponse.getJSONObject("artists").getJSONArray("items").get(0);

                    logger.info("##########################################");
                    logger.info("Popularity: "+something.getLong("popularity"));
                    popularity=something.getLong("popularity");
                    logger.info("Followers: "+something.getJSONObject("followers").getLong("total"));
                    logger.info("Name: "+something.getString("name"));
              
                }
            } else {
                System.out.println("POST request failed with response code: " + responseCode);
            }

            conn.disconnect();
            
		} catch (Exception e) {
			e.getMessage();
		}
		
		artst.setName(Long.toString(popularity));
		return artst;
	}

}
