package edu.uncc.mad.huduku;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import edu.uncc.mad.huduku.location.LocationHelper;
import edu.uncc.mad.huduku.pinning.SharedPreferenceManager;
import edu.uncc.mad.huduku.pinning.SharedPreferenceManager.LatLonAddress;

public class PinnedPlacesActivity extends Activity {

	private ListView pinnedPlacesListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pinned_places);
		
		Object [] pinnedPlacesObjects = SharedPreferenceManager.getSharedPreferenceManager().getPinnedPlacesNames().toArray();
		final String [] pinnedPlacesNames = new String [pinnedPlacesObjects.length];
		
		for(int i = 0 ; i < pinnedPlacesObjects.length; i ++)
			pinnedPlacesNames[i] = String.valueOf(pinnedPlacesObjects[i]);
		
		pinnedPlacesListView = (ListView) findViewById(R.id.pinnedPlacesListView);
		
		ArrayAdapter<String> pinnedPlacesAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, android.R.id.text1, pinnedPlacesNames);
		
		pinnedPlacesListView.setAdapter(pinnedPlacesAdapter);
		
		pinnedPlacesListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int clickedItemId,
					long arg3) {
				String restaurantName = pinnedPlacesNames[clickedItemId];
				LatLonAddress latLonAddress = SharedPreferenceManager.getSharedPreferenceManager().getLocation(restaurantName);
				double [] latLon = LocationHelper.getCurrentLocation();
				
				Intent intent = null;
				String addr = latLonAddress.getAddress();
				if(addr != null)
					addr = addr.trim().replace(' ', '+');
				
				if(latLonAddress != null && addr != null)
					intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + latLon[0] + "," + latLon[1]+ "&daddr=" + addr));
				else if(latLonAddress.getLatLon()[0] != 0.0 && latLonAddress.getLatLon()[0] != 0.0 )
					intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + latLon[0] + "," + latLon[1]+ "&daddr=" + latLonAddress.getLatLon()[0] + "," + latLonAddress.getLatLon()[1]));
				
				startActivity(intent);
				
			}
		});
		
		pinnedPlacesListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int clickedIndex, long arg3) {
				String restaurantName = pinnedPlacesNames[clickedIndex];
				SharedPreferenceManager.getSharedPreferenceManager().deletePinnedPlace(restaurantName);
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pinned_places, menu);
		return true;
	}

}
