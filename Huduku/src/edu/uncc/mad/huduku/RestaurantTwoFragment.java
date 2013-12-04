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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
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

public class RestaurantTwoFragment extends Fragment {

	private TextView restaurantTwo;
	private TextView ratingsRestTwo;

	private ImageView dealsForRest2;
	private ImageView pinLocation;
	private ImageView openWebActivity;

	private Restaurant rest2;

	private List<String> listStringData;
	private List<Review> rest2Reviews;
	private List<String> expandListHeader;
	private List<String> expandListContent;

	private Review rev1;
	private Review rev2;

	private String reviewText1;
	private String reviewText2;

	private ExpandableListView expandListView;
	private ExpandListAdapter expandListAdapter;
	private HashMap<String, String> expandListData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View restTwo = inflater.inflate(R.layout.fragment_restaurant_two,
				container, false);
		return restTwo;
	}

	public void renderRestaurantTwo(Restaurant r2) {

		rest2 = r2;

		if (r2.getName() != null) {
			restaurantTwo.setText(r2.getName().toUpperCase());
		} else {
			restaurantTwo.setText("NO RESTAURANT FOUND !!");
		}

		if (r2.getRating() > 0.0f) {
			ratingsRestTwo.setText("Restaurant Rating " + r2.getRating());
		} else {
			ratingsRestTwo.setText("NO RATING FOUND !!");
		}

		listStringData.clear();

		if (r2.getReviews() != null) {
			rest2Reviews = r2.getReviews();

			if (rest2Reviews != null && rest2Reviews.size() > 0) {

				Review rev1 = rest2Reviews.get(0);

				reviewText1 = rev1.getReviewText();
				listStringData.add(reviewText1);

				if (rest2Reviews.size() > 1) {
					Review rev2 = rest2Reviews.get(1);
					reviewText2 = rev2.getReviewText();
					listStringData.add(reviewText2);
				}
			}
		}

		prepareListData(listStringData, listStringData);
		expandListAdapter = new ExpandListAdapter(getActivity()
				.getApplicationContext(), expandListHeader, expandListData);
		expandListView.setAdapter(expandListAdapter);

		dealsForRest2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("huduku", "Deals icon clicked");

				if (rest2.getDeals() == null || rest2.getDeals().size() == 0) {
					Toast.makeText(getActivity().getApplicationContext(),
							"No Deals available for restaurant",
							Toast.LENGTH_LONG).show();
					return;
				}

				ArrayList<String> dealsText = new ArrayList<String>();
				ArrayList<String> dealsUrl = new ArrayList<String>();

				List<Deal> restDealsList = rest2.getDeals();
				for (int i = 0; restDealsList != null
						&& i < restDealsList.size(); i++) {
					dealsText.add(i, restDealsList.get(i).getTitle());
					dealsUrl.add(i, restDealsList.get(i).getBuyUrl());
				}

				Intent i = new Intent(getActivity().getApplicationContext(),
						DealsActivity.class);
				i.putExtra(CoreConstants.RESTAURANT_NAME, rest2.getName());
				i.putStringArrayListExtra(CoreConstants.DEALS_TEXT_LIST,
						dealsText);
				i.putStringArrayListExtra(CoreConstants.DEALS_TEXT_URLS,
						dealsUrl);

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
				int limit = CoreConstants.HEADER_LENGTH;

				if (smallStringData.get(i).length() < limit)
					limit = smallStringData.get(i).length();

				expandListHeader
						.add(smallStringData.get(i).substring(0, limit));
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

		ratingsRestTwo = (TextView) getView().findViewById(
				R.id.textViewRatingsRestaurant2);
		restaurantTwo = (TextView) getView().findViewById(
				R.id.textViewRestaurantTwo);

		// get the listview
		expandListView = (ExpandableListView) getView().findViewById(
				R.id.expandableListView2);

		dealsForRest2 = (ImageView) getView()
				.findViewById(R.id.imageViewDeals2);
		pinLocation = (ImageView) getView().findViewById(R.id.imageViewPin2);
		openWebActivity = (ImageView) getView()
				.findViewById(R.id.imageViewWeb2);

		pinLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (SharedPreferenceManager.getSharedPreferenceManager()
						.getPinnedPlacesCount() == CoreConstants.MAX_PINNED_PLACES) {
					Toast.makeText(getActivity().getApplicationContext(),
							"Pinned Place Locations full!", Toast.LENGTH_LONG)
							.show();
					return;
				}

				SharedPreferenceManager.getSharedPreferenceManager()
						.saveLocation(rest2.getName(), rest2.getLocation()[0],
								rest2.getLocation()[1], rest2.getAddress());
				Toast.makeText(getActivity().getApplicationContext(),
						"Pinned Place", Toast.LENGTH_LONG).show();
			}
		});

		openWebActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (rest2.getUrl() == null) {
					Toast.makeText(getActivity().getApplicationContext(),
							"No URL available for restaurant",
							Toast.LENGTH_LONG).show();
					return;
				}

				Intent i = new Intent(getActivity().getApplicationContext(),
						URLActivity.class);
				i.putExtra(CoreConstants.URL_HELPER_PAGE_TITLE, rest2.getName());

				i.putExtra(CoreConstants.URL_HELPER_PAGE_URL, rest2.getUrl());

				startActivity(i);

			}
		});
	}
}