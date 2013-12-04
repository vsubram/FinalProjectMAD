package edu.uncc.mad.huduku.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import edu.uncc.mad.huduku.core.Deal;
import edu.uncc.mad.huduku.core.GooglePlacesReview;
import edu.uncc.mad.huduku.core.Restaurant;
import edu.uncc.mad.huduku.core.Review;
import edu.uncc.mad.huduku.core.constants.SearchConstants;

public class GooglePlacesJSONParserImpl implements Parser {

	@Override
	public List<Restaurant> getRestaurantsFrom(String JSON) {		
		
		int parsedRestaurants = 0;
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		try {
			JSONObject responseJSONObject = new JSONObject(JSON);
			
			if(responseJSONObject.getString("status").equalsIgnoreCase("ok")){
				JSONArray restaurantJSONArray = responseJSONObject.getJSONArray("results");
				
				for(int i = 0 ; i < restaurantJSONArray.length(); i ++){
					Restaurant restaurant = new Restaurant();
					JSONObject currentRestaurantJSONObject = restaurantJSONArray.getJSONObject(i);
					
					restaurant.setName(currentRestaurantJSONObject.getString("name"));
					restaurant.setId(currentRestaurantJSONObject.getString("reference"));
					
					if(currentRestaurantJSONObject.has("rating"))
						restaurant.setRating(currentRestaurantJSONObject.getDouble("rating"));
					
					double [] latLon = new double[2];		
					
					latLon [0] = currentRestaurantJSONObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
					latLon [1] = currentRestaurantJSONObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
					
					restaurant.setLocation(latLon);
					if(currentRestaurantJSONObject.has("url"))
						restaurant.setUrl(currentRestaurantJSONObject.getString("url"));
					restaurants.add(restaurant);
					
					if(++parsedRestaurants > Integer.parseInt(SearchConstants.SEARCH_LIMIT))
						break;
					
				}
			} else {
				Log.d("ankur", "Failed with: " + responseJSONObject.getString("status"));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return restaurants;
	}

	@Override
	public List<Review> getReviewsFrom(JSONObject restaurant) throws JSONException {
		
		JSONArray reviewsJSONArray = restaurant.getJSONObject("result").getJSONArray("reviews");
		List<Review> reviews = new ArrayList<Review>();
		
		for(int i = 0 ; i < reviewsJSONArray.length(); i ++){
			GooglePlacesReview review = new GooglePlacesReview();
			JSONObject currentReview = reviewsJSONArray.getJSONObject(i);
			JSONArray reviewAspects = currentReview.getJSONArray("aspects"); 
			
			for(int j = 0 ; j < reviewAspects.length(); j ++){
				JSONObject currentAspect = reviewAspects.getJSONObject(j);
				review.addRating(currentAspect.getString("type"), currentAspect.getDouble("rating"));
			}
			
			review.setReviewRating(currentReview.getDouble("rating"));
			review.setReviewText(currentReview.getString("text"));
			
			if(currentReview.has("author_url")) 
				review.setAuthorURL(currentReview.getString("author_url"));
			
			review.setUserName(currentReview.getString("author_name"));
			review.setTimePosted(currentReview.getLong("time"));
			
			reviews.add(review);
			
		}

		return reviews;
	}

	@Override
	public List<Deal> getDealsFrom(JSONObject restaurant) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}
}
