package edu.uncc.mad.huduku;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnHoverListener;
import android.view.Window;
import android.widget.RelativeLayout;

import edu.uncc.mad.huduku.core.Restaurant;
import edu.uncc.mad.huduku.core.constants.CoreConstants;
import edu.uncc.mad.huduku.location.LocationHelper;
import edu.uncc.mad.huduku.observer.LocationChangeObserver;
import edu.uncc.mad.huduku.observer.LocationObservable;
import edu.uncc.mad.huduku.servicedatagetters.CityGridGetter;
import edu.uncc.mad.huduku.servicedatagetters.GooglePlacesGetter;
import edu.uncc.mad.huduku.servicedatagetters.YelpGetter;

public class ProviderActivity extends Activity implements LocationChangeObserver {

	private static Handler handler;
	private static ExecutorService es;

	private static ProgressDialog dialog;
	private static LocationManager locationManager;

	private RelativeLayout relativeLayout1;
	private RelativeLayout relativeLayout2;

	private RestaurantOneFragment restaurantFragOne;
	private RestaurantTwoFragment restaurantFragTwo;

	private FragmentManager fragManager;
	private FragmentTransaction fragTransaction;
	
	private Restaurant restaurant1;
	private Restaurant restaurant2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_provider);
		setTitle(getIntent().getStringExtra("TITLE"));

		dialog = new ProgressDialog(ProviderActivity.this);
		dialog.setCancelable(false);
		dialog.setMessage("Getting data from internet");

		
		
		es = Executors.newFixedThreadPool(1);

		/**
		 * Initialize the location handler. This must be done in all the
		 * location
		 */
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationHelper.setLocationManager(locationManager);
		LocationHelper.registerLocationObserver(this);

		handler = new Handler(new Callback() {

			/**
			 * NOTE: The handleMessage has to be marked with the synchronized
			 * keyword. This handler is invoked by threads and its possible that
			 * many threads access this at a time.
			 */

			@Override
			synchronized public boolean handleMessage(Message msg) {
				dialog.dismiss();

				/**
				 * TODO Setup the fragments here. The data from the provider can
				 * be extracted as: ArrayList<Restaurant> restaurants =
				 * msg.getData().getParcelableArrayList("RESTAURANTS");
				 * 
				 */
				ArrayList<Restaurant> restaurants = msg.getData()
						.getParcelableArrayList("RESTAURANTS");
				Log.d("huduku",
						"Number of restaurants fetched: " + restaurants.size());
				
				/**
				 * Get the first two objects of Restaurant Array List 
				 */
				restaurant1 = restaurants.get(0);
				restaurant2 = restaurants.get(1);
				
				Log.d("huduku", "Rest Name 1 " + restaurant1.getName());
				Log.d("huduku", "Rest Name 2 " + restaurant2.getName());
				
				Log.d("huduku", "Rest Rate 1 " + restaurant1.getRating());
				Log.d("huduku", "Rest Name 2 " + restaurant2.getRating());
				
				restaurantFragOne.renderRestaurantOne(restaurant1);
				restaurantFragTwo.renderRestaurantTwo(restaurant2);
				
				/**
				 * This is how we call the URLActivity.
				 * 
				 * Intent intent = new Intent(getApplicationContext(),
				 * URLActivity.class);
				 * intent.putExtra(CoreConstants.URL_HELPER_PAGE_TITLE,
				 * "Test Title");
				 * intent.putExtra(CoreConstants.URL_HELPER_PAGE_URL,
				 * "http://developer.android.com/guide/webapps/webview.html");
				 * startActivity(intent);
				 * 
				 */
				return false;
			}
		});

		/**
		 * In the button handlers, invoke the new activity with fragments. In
		 * that activity on create method get the current location like below
		 * statements and get the initial restaurants. Make the activity
		 * implement the LocationObservable interface. That will then be called
		 * whenever there is a location change.
		 */
		// double[] currentLocation = locationObservable.getCurrentLocation();
		// onLocationChanged(currentLocation[0], currentLocation[1]);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override protected void onResume(){
		super.onResume();
		
		/**
		 * Initialize both fragments present in the activity using the
		 * FragmentManager and Fragment Transaction
		 */
		relativeLayout1 = (RelativeLayout) findViewById(R.id.relativeLayout1);
		relativeLayout2 = (RelativeLayout) findViewById(R.id.relativeLayout2);

		fragManager = getFragmentManager();
		
		restaurantFragOne = (RestaurantOneFragment)fragManager.findFragmentById(R.id.fragmentRestaurant1);
		restaurantFragTwo = (RestaurantTwoFragment) fragManager.findFragmentById(R.id.fragmentRestaurant2);
		
		fragTransaction = fragManager.beginTransaction();

		relativeLayout1.setOnHoverListener(new OnHoverListener() {

			@Override
			public boolean onHover(View v, MotionEvent event) {
				fragTransaction.replace(R.id.fragmentRestaurant1, restaurantFragOne);
				return true;
			}
		});

		relativeLayout2.setOnHoverListener(new OnHoverListener() {

			@Override
			public boolean onHover(View v, MotionEvent event) {
				fragTransaction.replace(R.id.fragmentRestaurant2, restaurantFragTwo);
				return true;
			}
		});

		fragTransaction.commit();
		
	}

	@Override
	public void onLocationChanged(double latitude, double longitude) {
		String provider = getIntent().getStringExtra("PROVIDER");

		if(restaurantFragOne == null || restaurantFragTwo == null){
			Log.d("huduku", "Fragments null");
			return;
		}
		
		if(!restaurantFragOne.isVisible() || !restaurantFragTwo.isVisible()){
			Log.d("huduku", "fragments not visible yet");
			return;
		}
		
		if (!isFinishing())
			dialog.show();

		if (provider.equals(CoreConstants.YELP_PROVIDER_HANDLE)) {
			es.execute(new YelpGetter(handler, latitude, longitude));
			Log.d("huduku", "Loading with new location and yelp provider: "
					+ latitude + "," + longitude);
		} else if (provider.equals(CoreConstants.CG_PROVIDER_HANDLE)) {
			es.execute(new CityGridGetter(handler, latitude, longitude));
			Log.d("huduku", "Loading with new location and citygrid provider: "
					+ latitude + "," + longitude);
		} else if (provider.equals(CoreConstants.GOOGLE_PROVIDER_HANDLE)) {
			es.execute(new GooglePlacesGetter(handler, latitude, longitude));
			Log.d("huduku",
					"Loading with new location and google places provider: "
							+ latitude + "," + longitude);
		}
	}

	@Override
	public void registerWithLocationChangeObservable(
			LocationObservable locationChangeObservable) {
		locationChangeObservable.registerLocationObserver(this);
	}
}
