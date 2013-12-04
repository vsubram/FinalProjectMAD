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
import edu.uncc.mad.huduku.parsers.CityGridJSONParserImpl;
import edu.uncc.mad.huduku.providers.citygrid.CityGrid;

public class CityGridGetter implements Runnable {
	
	private double lat;
	private double lon;
	private Handler handler;
	
	public CityGridGetter(Handler handler, double lat, double lon ){
		this.lat = lat;
		this.lon = lon;
		this.handler = handler;
	}
	
	@Override
	public void run(){
		CityGrid cg = new CityGrid();
		String restaurantsJSONString = cg.placesSearch(lat, lon);
		CityGridJSONParserImpl parser = new CityGridJSONParserImpl(); 
		List<Restaurant> restaurants = parser.getRestaurantsFrom(restaurantsJSONString);
		
		for(Restaurant r: restaurants){
			try {
				r.setReviews(parser.getReviewsFrom(new JSONObject(cg.reviewsSearch(r.getId()))));
				Log.d("huduku", "City Grid Restaurant: " + r.getName() + " with reviews: " + r.getReviews().size());
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

