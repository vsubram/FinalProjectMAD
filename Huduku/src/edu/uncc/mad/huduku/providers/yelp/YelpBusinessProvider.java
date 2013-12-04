package edu.uncc.mad.huduku.providers.yelp;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uncc.mad.huduku.core.Restaurant;
import edu.uncc.mad.huduku.core.Review;
import edu.uncc.mad.huduku.parsers.YelpJSONParserImpl;

public class YelpBusinessProvider {
	private Restaurant restaurant;
	
	public YelpBusinessProvider(Restaurant restaurant){
		this.restaurant = restaurant;
	}

	public Restaurant getRestaurantReviews() {
		String businessSearchJSON = new Yelp().searchBusiness(restaurant.getId());
		
		List<Review> reviews = null;
		
		try {
			reviews = new YelpJSONParserImpl().getReviewsFrom(new JSONObject(businessSearchJSON));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		restaurant.setReviews(reviews);
		return restaurant;
	}
}