package edu.uncc.mad.huduku.core;

import java.util.HashMap;
import java.util.Map;

public class GooglePlacesReview extends Review {
	
	private Map<String, Double> ratingsMap;
	
	public GooglePlacesReview(){
		super();
		ratingsMap = new HashMap<String, Double>();
	}

	public Map<String, Double> getRatingsMap() {
		return ratingsMap;
	}

	public void setRatingsMap(Map<String, Double> ratingsMap) {
		this.ratingsMap = ratingsMap;
	}
	
	public void addRating(String type, Double rating){
		ratingsMap.put(type, rating);
	}
}
