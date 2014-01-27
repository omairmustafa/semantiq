package image;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class GoogleImagesLoader implements ImagesLoader{

	public List<String> getImageUrls(String keyword) {

		JSONObject googleResponse, responseDataJSON, imageJSON;
		JSONArray resultsArray;
		List<String> imageUrls = null;
		
		try {

			//Google images search Url construction
			URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?" +
					"v=1.0&q=" + keyword);
			
			URLConnection connection = url.openConnection();
			
			//Read the response in a string
			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while((line = reader.readLine()) != null) {
				builder.append(line);
			}

			//Parse response in terms of JSON object
			googleResponse = new JSONObject(builder.toString());
			responseDataJSON = googleResponse.getJSONObject("responseData");
			resultsArray = responseDataJSON.getJSONArray("results");
			imageUrls = new ArrayList<String>();
			
			
			for (int resultsCount = 0; resultsCount < resultsArray.length(); resultsCount++) {

				imageJSON = resultsArray.getJSONObject(resultsCount);
				imageUrls.add(imageJSON.get("unescapedUrl").toString());
			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return imageUrls;
	}

}
