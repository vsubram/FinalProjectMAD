package edu.uncc.mad.huduku.providers.yelp;

import edu.uncc.mad.huduku.core.constants.SearchConstants;

public class YelpSearchProvider {
	private double latitude;
	private double longitude;
	
	public YelpSearchProvider(double latitude, double longitude){
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public String getSearchResults() {
		String searchResponse = new Yelp().search(SearchConstants.SEARCH_KEYWORD, latitude, longitude);
		return searchResponse;
	}
}