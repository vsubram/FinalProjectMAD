package edu.uncc.mad.huduku.parsers;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uncc.mad.huduku.core.Deal;
import edu.uncc.mad.huduku.core.Restaurant;
import edu.uncc.mad.huduku.core.Review;

public interface Parser {
	
	List<Restaurant> getRestaurantsFrom(String JSON);
	List<Review> getReviewsFrom(JSONObject restaurant) throws JSONException;
	List<Deal> getDealsFrom(JSONObject restaurant) throws JSONException;
}
