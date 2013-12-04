package edu.uncc.mad.huduku.location;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

public class GeoCodeHelper {
	
	private static Context c;
	
	public static void initializeGeoCodeHelper(Context c){
		GeoCodeHelper.c = c;
	}

	public static double [] getLatLongFor(String address){
		if(c == null)
			return null;
		
		if(!Geocoder.isPresent())
			Log.d("huduku", "Geocoder not present");
		
		double [] latLong = new double[2];
		List<Address> addresses = null;
		try {
			addresses = new Geocoder(c).getFromLocationName(address, 5);
			if(addresses.size() > 0) {
			    latLong[0] = addresses.get(0).getLatitude();
			    latLong[1] = addresses.get(0).getLongitude();
			}
			return latLong;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
