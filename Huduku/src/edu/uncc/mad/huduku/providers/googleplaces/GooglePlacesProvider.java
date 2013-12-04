package edu.uncc.mad.huduku.providers.googleplaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class GooglePlacesProvider {
	
	private static final String API_KEY = "AIzaSyCMriEx204jQ58c8-I_-hyqEyzwHYJX3fc";
	
	private String getResultsFromURL(List<NameValuePair> urlParams, String urlPath){
		HttpClient client = new DefaultHttpClient();
		BufferedReader in = null;
		String json = "";
		
		URI url;
		try {
			url = org.apache.http.client.utils.URIUtils.createURI("https", "maps.googleapis.com", -1, urlPath, URLEncodedUtils.format(urlParams, "UTF-8"), null);
			
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);
			
			if(response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				StringBuffer sb = new StringBuffer();
				String line = "";
				
				while((line = in.readLine()) != null){
					sb.append(line + "\n");
				}
				
				json = sb.toString();
			} else {
				Log.d("ankur", "Something went wrong");
			}
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		return json;
	}
	
	public String getGoogleRestaurantData(String restaurantReference){
		List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
		urlParams.add(new BasicNameValuePair("reference", restaurantReference));
		urlParams.add(new BasicNameValuePair("sensor", "true"));
		urlParams.add(new BasicNameValuePair("key", API_KEY));
		
		return getResultsFromURL(urlParams, "/maps/api/place/details/json");
	}
	
	public String getGoogleSearchRestaurants(double lat, double lon){
		
		List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
		urlParams.add(new BasicNameValuePair("location", lat + "," + lon));
//		urlParams.add(new BasicNameValuePair("radius", "200"));
		urlParams.add(new BasicNameValuePair("sensor", "true"));
		urlParams.add(new BasicNameValuePair("types", "restaurant"));
		urlParams.add(new BasicNameValuePair("rankby", "distance"));
		urlParams.add(new BasicNameValuePair("key", API_KEY));
		
		return getResultsFromURL(urlParams, "/maps/api/place/nearbysearch/json");
	}
}
