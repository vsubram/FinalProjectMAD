package edu.uncc.mad.huduku;

import java.util.ArrayList;
import java.util.Set;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import edu.uncc.mad.huduku.core.constants.CoreConstants;
import edu.uncc.mad.huduku.location.GeoCodeHelper;
import edu.uncc.mad.huduku.location.LocationHelper;
import edu.uncc.mad.huduku.pinning.SharedPreferenceManager;
import edu.uncc.mad.huduku.utils.GuiArrayAdapter;

public class MainActivity extends Activity {

	private ListView listViewReviews;
	private GuiArrayAdapter guiArrayAdapter;
	private GoogleMap gMap;
	private MapFragment mapFrag;
	private LocationManager locManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/**
		 * Setting GoogleMap on home screen
		 */

		mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		gMap = mapFrag.getMap();
		gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		gMap.setMyLocationEnabled(true);

		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		Location l = locManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
				new LatLng(l.getLatitude(), l.getLongitude()), 17));

		/**
		 * Initialize Location Manager.
		 */
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationHelper.setLocationManager(lm);

		/**
		 * Initialize Geo Coder
		 */
		GeoCodeHelper.initializeGeoCodeHelper(getApplicationContext());

		/**
		 * List View Initializations
		 */
		listViewReviews = (ListView) findViewById(R.id.listViewReviews);

		/**
		 * 
		 * Creating a list view adapter
		 */

		ArrayList<ImageView> logos = new ArrayList<ImageView>();

		ImageView iv = new ImageView(getApplicationContext());
		iv.setImageResource(R.drawable.yelp_logo);
		logos.add(0, iv);

		ImageView iv1 = new ImageView(getApplicationContext());
		iv1.setImageResource(R.drawable.citygrid_logo);
		logos.add(0, iv1);

		ImageView iv2 = new ImageView(getApplicationContext());
		iv2.setImageResource(R.drawable.google_places_logo);
		logos.add(0, iv2);

		guiArrayAdapter = new GuiArrayAdapter(this, logos);

		// setting values for the newly created list view
		listViewReviews.setAdapter(guiArrayAdapter);
		listViewReviews.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int listMenuValue, long arg3) {

				Intent intentProviderActivity = new Intent(
						getApplicationContext(), ProviderActivity.class);

				switch (listMenuValue) {
				case 0:
					intentProviderActivity.putExtra("PROVIDER",
							CoreConstants.YELP_PROVIDER_HANDLE);
					intentProviderActivity.putExtra("TITLE",
							CoreConstants.YELP_PROVIDER_TITLE);
					break;

				case 1:
					intentProviderActivity.putExtra("PROVIDER",
							CoreConstants.CG_PROVIDER_HANDLE);
					intentProviderActivity.putExtra("TITLE",
							CoreConstants.CG_PROVIDER_TITLE);
					break;

				case 2:
					intentProviderActivity.putExtra("PROVIDER",
							CoreConstants.GOOGLE_PROVIDER_HANDLE);
					intentProviderActivity.putExtra("TITLE",
							CoreConstants.GOOGLE_PROVIDER_TITLE);
					break;
				}

				startActivity(intentProviderActivity);
			}
		});

		/**
		 * Initialize the Pinned places handler module
		 */
		SharedPreferenceManager.createInstance(getApplication(),
				getString(R.string.PINNED_PLACES_FILE_NAME));

		/**
		 * USAGE: SharedPreferenceManager manager =
		 * SharedPreferenceManager.getSharedPreferenceManager();
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.pinnedPlacesMenuItem:
			
			Log.d("huduku", "Pinned Places");

			if (SharedPreferenceManager.getSharedPreferenceManager()
					.getPinnedPlacesCount() == 0) {
				Toast.makeText(getApplicationContext(), "No Places Pinned yet",
						Toast.LENGTH_LONG).show();
				return true;
			}

			startActivity(new Intent(getApplicationContext(),
					PinnedPlacesActivity.class));
			return true;

		case R.id.deletePinnedItemsItem:
			Set<String> pinnedPlacesNames = SharedPreferenceManager
					.getSharedPreferenceManager().getPinnedPlacesNames();

			if (pinnedPlacesNames == null)
				return true;

			for (String s : pinnedPlacesNames)
				SharedPreferenceManager.getSharedPreferenceManager()
						.deletePinnedPlace(s);

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}