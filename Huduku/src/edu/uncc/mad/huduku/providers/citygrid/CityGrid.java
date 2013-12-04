package edu.uncc.mad.huduku.providers.citygrid;

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

import edu.uncc.mad.huduku.core.constants.SearchConstants;

import android.util.Log;

public class CityGrid {
    
    public String reviewsSearch(String restaurantId){
    	List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
		urlParams.add(new BasicNameValuePair("format", "json"));
		urlParams.add(new BasicNameValuePair("publisher", "10000005417"));
		urlParams.add(new BasicNameValuePair("rpp", "6"));
		urlParams.add(new BasicNameValuePair("listing_id", restaurantId));

		HttpClient client = new DefaultHttpClient();
		BufferedReader in = null;
		
		String json = "";
		
		try {
			URI url = org.apache.http.client.utils.URIUtils.createURI("http", "api.citygridmedia.com", -1, "/content/reviews/v2/search/latlon", 
					URLEncodedUtils.format(urlParams, "UTF-8"), null);
			
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
			try {
				if(in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return json;
    }
	
	public String placesSearch(double lat, double lon){
		
		List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
		urlParams.add(new BasicNameValuePair("format", "json"));
		urlParams.add(new BasicNameValuePair("lat", String.valueOf(lat)));
		urlParams.add(new BasicNameValuePair("lon", String.valueOf(lon)));
		urlParams.add(new BasicNameValuePair("radius", SearchConstants.RADIUS_FEET));
		urlParams.add(new BasicNameValuePair("what", SearchConstants.SEARCH_KEYWORD));
		urlParams.add(new BasicNameValuePair("publisher", "10000005417"));
		urlParams.add(new BasicNameValuePair("rpp", "10"/*SearchConstants.SEARCH_LIMIT*/));
		urlParams.add(new BasicNameValuePair("sort", "dist"));
		HttpClient client = new DefaultHttpClient();
		BufferedReader in = null;
		
		String json = "";
		
		try {
			URI url = org.apache.http.client.utils.URIUtils.createURI("http", "api.citygridmedia.com", -1, "/content/places/v2/search/latlon", 
					URLEncodedUtils.format(urlParams, "UTF-8"), null);
			
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
			try {
				if(in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return json;
	}

}
