package edu.uncc.mad.huduku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import edu.uncc.mad.huduku.activities.URLActivity;
import edu.uncc.mad.huduku.core.Deal;
import edu.uncc.mad.huduku.core.Restaurant;
import edu.uncc.mad.huduku.core.Review;
import edu.uncc.mad.huduku.core.constants.CoreConstants;
import edu.uncc.mad.huduku.pinning.SharedPreferenceManager;
import edu.uncc.mad.huduku.utils.ExpandListAdapter;

public class RestaurantOneFragment extends Fragment {

	private TextView restaurantOne;
	private TextView ratingsRestOne;
	
	private ImageView dealsForRest1;
	private ImageView pinLocation;
	private ImageView openWebActivity;
	
	private Restaurant rest1;
	
	private Review rev1;
	private Review rev2;
	
	private List<Review> rest1Reviews;
	private List<String> listStringData;
	private List<String> expandListHeader;
	private List<String> expandListContent;
	
	private String reviewText1;
	private String reviewText2;

	private ExpandableListView expandListView;
	private ExpandListAdapter expandListAdapter;
	private HashMap<String, String> expandListData;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View restOne = inflater.inflate(R.layout.fragment_restaurant_one,
				container, false);

		return restOne;
	}

	public void renderRestaurantOne(Restaurant r1) {
		
		rest1 = r1;
		
		if (r1.getName() != null) {
			restaurantOne.setText(r1.getName().toUpperCase());
		} else {
			restaurantOne.setText("NO RESTAURANT FOUND !!");
		}

		if (r1.getRating() > 0.0f) {
			ratingsRestOne.setText("Restaurant Rating " + r1.getRating());
		} else {
			ratingsRestOne.setText("NO RATING FOUND !!");
		}

		listStringData.clear();

		if (r1.getReviews() != null) {
			rest1Reviews = r1.getReviews();

			if (rest1Reviews != null && rest1Reviews.size() > 0) {

				rev1 = rest1Reviews.get(0);
				reviewText1 = rev1.getReviewText();
				listStringData.add(reviewText1);

				if (rest1Reviews.size() > 1) {
					rev2 = rest1Reviews.get(1);
					reviewText2 = rev2.getReviewText();
					listStringData.add(reviewText2);
				}
			}
		}
		
		/**
		 * Prepares the data set of headers and contents for Expnadable List
		 */
		prepareListData(listStringData, listStringData);
		
		/**
		 * Invokes the Expandlist Adapter required to set the information
		 */
		expandListAdapter = new ExpandListAdapter(getActivity()
				.getApplicationContext(), expandListHeader, expandListData);
		
		/**
		 * Sets the adapter to the Expandlist View
		 */
		expandListView.setAdapter(expandListAdapter);
		
		/**
		 * Perform On-click listen when the deals icon is clicked in the fragment
		 */
		dealsForRest1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("huduku", "Deals icon clicked");
				
				if(rest1.getDeals() == null || rest1.getDeals().size() == 0){
					Toast.makeText(getActivity().getApplicationContext(), "No Deals available for restaurant", Toast.LENGTH_LONG).show();
					return;
				}
				
				ArrayList<String> dealsText = new ArrayList<String>();
				ArrayList<String> dealsUrl = new ArrayList<String>();
				
				List<Deal> restDealsList = rest1.getDeals();
				for (int i = 0; restDealsList != null && i < restDealsList.size(); i++) {
					dealsText.add(i, restDealsList.get(i).getTitle());
					dealsUrl.add(i, restDealsList.get(i).getBuyUrl());
				}
				
				Intent i = new Intent(getActivity().getApplicationContext(), DealsActivity.class);
				i.putExtra(CoreConstants.RESTAURANT_NAME, rest1.getName());
				i.putStringArrayListExtra(CoreConstants.DEALS_TEXT_LIST, dealsText);
				i.putStringArrayListExtra(CoreConstants.DEALS_TEXT_URLS, dealsUrl);
				
				startActivity(i);
			}
		});
	}

	private void prepareListData(List<String> smallStringData,
			List<String> bigStringData) {

		expandListHeader = new ArrayList<String>();
		expandListContent = new ArrayList<String>();

		expandListData = new HashMap<String, String>();

		// Adding Headers
		if (listStringData.size() == 0) {
			expandListHeader.add("No Header Review found !!");
		} else {
			for (int i = 0; i < listStringData.size(); i++) {
				int limit = 60;
				
				if(smallStringData.get(i).length() < 60)
					limit = smallStringData.get(i).length();
				
				expandListHeader.add(smallStringData.get(i).substring(0, limit));
			}
		}
		
		// Adding Content
		if (listStringData.size() == 0) {
			expandListContent.add("No Content Review found !!");
		} else {
			for (int i = 0; i < listStringData.size(); i++) {
				expandListContent.add(bigStringData.get(i));
			}
		}
		
		if (listStringData.size() == 0) {
			expandListData.put("Header NULL", "Conent NULL");
		} else {
			for (int i = 0; i < listStringData.size(); i++) {
				expandListData.put(expandListHeader.get(i),
						expandListContent.get(i));
			}
		}
	}

	@Override
	public void onActivityCreated(Bundle b) {
		super.onActivityCreated(b);

		/**
		 * Initialize the listDataString Array list
		 */
		listStringData = new ArrayList<String>();

		ratingsRestOne = (TextView) getView().findViewById(
				R.id.textViewRatingsRestaurant1);
		restaurantOne = (TextView) getView().findViewById(
				R.id.textViewRestaurantOne);

		// get the listview
		expandListView = (ExpandableListView) getView().findViewById(
				R.id.expandableListView1);
		
		dealsForRest1 = (ImageView) getView().findViewById(R.id.imageViewDeals1);
		pinLocation = (ImageView) getView().findViewById(R.id.imageViewPin1);
		openWebActivity = (ImageView) getView().findViewById(R.id.imageViewWeb1);
		
		pinLocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(SharedPreferenceManager.getSharedPreferenceManager().getPinnedPlacesCount() == CoreConstants.MAX_PINNED_PLACES){
					Toast.makeText(getActivity().getApplicationContext(), "Pinned Place Locations full!", Toast.LENGTH_LONG).show();
					return;
				}
				
				if(rest1.getName() == null && rest1.getLocation()[0] == 0.0 && rest1.getLocation()[1] == 0.0){
					Toast.makeText(getActivity().getApplicationContext(), "No location details available!", Toast.LENGTH_LONG).show();
					return;
				}
				
				SharedPreferenceManager.getSharedPreferenceManager().saveLocation(rest1.getName(), rest1.getLocation()[0], rest1.getLocation()[1], rest1.getAddress());
				Toast.makeText(getActivity().getApplicationContext(), "Pinned Place", Toast.LENGTH_LONG).show();
			}
		});
		
		openWebActivity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(rest1.getUrl() == null){
					Toast.makeText(getActivity().getApplicationContext(), "No URL available for restaurant", Toast.LENGTH_LONG).show();
					return;
				}
				
				Intent i = new Intent(getActivity().getApplicationContext(), URLActivity.class);
				i.putExtra(CoreConstants.URL_HELPER_PAGE_TITLE, rest1.getName());
				
				i.putExtra(CoreConstants.URL_HELPER_PAGE_URL, rest1.getUrl());
				
				startActivity(i);
				
			}
		});
	}
}
