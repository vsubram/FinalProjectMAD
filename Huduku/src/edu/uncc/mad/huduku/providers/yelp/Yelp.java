package edu.uncc.mad.huduku.providers.yelp;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import edu.uncc.mad.huduku.core.constants.SearchConstants;
import edu.uncc.mad.huduku.core.constants.YelpConstants;


public class Yelp {
	


	private OAuthService service;
	private Token accessToken;

	/**
	 * Setup the Yelp API OAuth credentials.
	 * 
	 * OAuth credentials are available from the developer site, under Manage
	 * API access (version 2 API).
	 * 
	 * @param CONSUMER_KEY Consumer key
	 * @param CONSUMER_SECRET Consumer secret
	 * @param TOKEN Token
	 * @param TOKEN_SECRET Token secret
	 */
	public Yelp() {
		this.service = new ServiceBuilder().provider(YelpApi2.class).apiKey(YelpConstants.CONSUMER_KEY).apiSecret(YelpConstants.CONSUMER_SECRET).build();
		this.accessToken = new Token(YelpConstants.TOKEN, YelpConstants.TOKEN_SECRET);
	}

	/**
	 * Search with term and location.
	 * 
	 * @param term Search term
	 * @param latitude Latitude
	 * @param longitude Longitude
	 * @return JSON string response
	 */
	public String search(String term, double latitude, double longitude) {
		
		OAuthRequest request = new OAuthRequest(Verb.GET, YelpConstants.YELP_SEARCH_URL);
		request.addQuerystringParameter("term", term);
		request.addQuerystringParameter("radius_filter", SearchConstants.RADIUS_FEET);
		request.addQuerystringParameter("ll", latitude + "," + longitude);
		request.addQuerystringParameter("sort", "1");
		request.addQuerystringParameter("limit", SearchConstants.SEARCH_LIMIT);
		
		this.service.signRequest(this.accessToken, request);
		
		Response response = request.send();
		return response.getBody();
	}
	
	public String searchBusiness(String businessId){
		OAuthRequest request = new OAuthRequest(Verb.GET, YelpConstants.YELP_BUSINESS_URL + businessId);
		this.service.signRequest(this.accessToken, request);
		Response response = request.send();
		return response.getBody();
	}
}