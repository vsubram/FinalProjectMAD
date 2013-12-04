package edu.uncc.mad.huduku.servicedatagetters;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import edu.uncc.mad.huduku.core.Restaurant;
import edu.uncc.mad.huduku.parsers.GooglePlacesJSONParserImpl;
import edu.uncc.mad.huduku.providers.googleplaces.GooglePlacesProvider;

public class GooglePlacesGetter implements Runnable {
	
	private Handler handler;	
	private double latitude;
	private double longitude;
	
	public GooglePlacesGetter(Handler handler, double latitude, double longitude){
		this.handler = handler;
		this.latitude = latitude;
		this.longitude = longitude;	
	}

	@Override
	public void run() {
		GooglePlacesProvider googlePlacesProvider = new GooglePlacesProvider();
		GooglePlacesJSONParserImpl googleJSONParser = new GooglePlacesJSONParserImpl();
		
		String searchJSONResponse = googlePlacesProvider.getGoogleSearchRestaurants(latitude, longitude);
		
		List<Restaurant> restaurants = googleJSONParser.getRestaurantsFrom(searchJSONResponse);
				
		for(Restaurant r: restaurants){
			try {
				r.setReviews(googleJSONParser.getReviewsFrom(new JSONObject(googlePlacesProvider.getGoogleRestaurantData(r.getId()))));
				Log.d("huduku", "Google Restaurant: " + r.getName() + " with reviews: " + r.getReviews().size());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		Message msg = new Message();
		Bundle bundle = new Bundle();
		
		bundle.putParcelableArrayList("RESTAURANTS", (ArrayList<Restaurant>) restaurants);
		msg.setData(bundle);
		
		handler.sendMessage(msg);
		
	}
}