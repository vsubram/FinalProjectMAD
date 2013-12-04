package edu.uncc.mad.huduku.servicedatagetters;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import edu.uncc.mad.huduku.core.Restaurant;
import edu.uncc.mad.huduku.parsers.YelpJSONParserImpl;
import edu.uncc.mad.huduku.providers.yelp.YelpBusinessProvider;
import edu.uncc.mad.huduku.providers.yelp.YelpSearchProvider;

public class YelpGetter implements Runnable {
	
	private Handler handler;
	private double latitude;
	private double longitude;
	
	public YelpGetter(Handler handler, double latitude, double longitude){
		this.handler = handler;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public void run() {
		String searchJSONResponse = new YelpSearchProvider(latitude, longitude).getSearchResults();
		
		List<Restaurant> restaurants = new YelpJSONParserImpl().getRestaurantsFrom(searchJSONResponse);
		YelpBusinessProvider yelpBusinessProvider; 
		
		for(Restaurant r: restaurants){
			yelpBusinessProvider = new YelpBusinessProvider(r);
			r = yelpBusinessProvider.getRestaurantReviews();
			if(r != null && r.getReviews() != null)
				Log.d("huduku", "YELP - Name: " + r.getName() + ". No of Reviews: " + r.getReviews().size());
		}
		
		Message msg = new Message();
		Bundle bundle = new Bundle();
		
		bundle.putParcelableArrayList("RESTAURANTS", (ArrayList<Restaurant>) restaurants);
		msg.setData(bundle);
		
		handler.sendMessage(msg);
		
	}
}