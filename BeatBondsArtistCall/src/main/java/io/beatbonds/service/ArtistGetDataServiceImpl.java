package io.beatbonds.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.beatbonds.model.Artist;
import io.beatbonds.model.ArtistFromSpotify;
import io.beatbonds.shared.SharedTokenData;

@Service
public class ArtistGetDataServiceImpl implements ArtistGetDataService {
	
	@Autowired
	private SharedTokenData sharedData;

	@Override
	public ArtistFromSpotify getArtistSpotifyData(Artist item) {

		ArtistFromSpotify artstDb = new ArtistFromSpotify();
		
		// Need environment variable
		String apiUrl = "https://api.spotify.com/v1/search";
		
		String q = item.getName().contains(" ")?item.getName().replaceAll(" ", "%20") : item.getName(); 

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

                    JSONObject itemsArr = new JSONObject();
                    JSONObject imageArr = new JSONObject();
                    itemsArr = (JSONObject) jsonResponse.getJSONObject("artists").getJSONArray("items").get(0);
                    imageArr = (JSONObject)itemsArr.getJSONArray("images").get(0);
                    image = imageArr.getString("url");
                    
                    System.out.println("##########################################");
//                    logger.info("##########################################");

                    popularity=itemsArr.getLong("popularity");
                    followers=itemsArr.getJSONObject("followers").getLong("total");
                    nameArt=itemsArr.getString("name");
                    image = imageArr.getString("url");
                    System.out.println(popularity+" "+followers+" "+nameArt+" "+image);

//                    logger.info(popularity+" "+followers+" "+nameArt+" "+image);
              
                }
            } else {
                System.out.println("POST request failed with response code: " + responseCode);
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

}