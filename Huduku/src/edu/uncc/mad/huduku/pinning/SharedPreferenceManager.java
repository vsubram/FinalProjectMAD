package edu.uncc.mad.huduku.pinning;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {

	private SharedPreferences pinnedPlacesFile;
	private static SharedPreferenceManager pinnedFileManager;
	private static int pinnedPlacesCount = 0;
	
	private static boolean isSingletonInitialized = false;
	
	private SharedPreferenceManager(Context context, String fileName){
		pinnedPlacesFile = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		
		@SuppressWarnings("unchecked")
		Map<String, Set<String>> pinnedPlaces = (Map<String, Set<String>>) pinnedPlacesFile.getAll();
		
		if(pinnedPlaces == null)
			pinnedPlacesCount = 0;
		else
			pinnedPlacesCount = pinnedPlaces.size();
		
	}
	
	@SuppressWarnings("unchecked")
	public Set<String> getPinnedPlacesNames(){
		
		if(pinnedPlacesCount == 0)
			return null;
		
		Map<String, String> placeLatLongPairs = (Map<String, String>) pinnedPlacesFile.getAll();
		return placeLatLongPairs.keySet();
		}

	public static void createInstance(Context context, String fileName){
		pinnedFileManager = new SharedPreferenceManager(context, fileName);
		isSingletonInitialized = true;
	}
	
	public static SharedPreferenceManager getSharedPreferenceManager(){
		if(!isSingletonInitialized)
			return null;
		
		return pinnedFileManager;
	}
		
	public void saveLocation(String name, double lat, double lon, String address){
		
		if(pinnedPlacesCount == 5)
			return;
		
		Set<String> latLonSet = new HashSet<String>();
		latLonSet.add("lat:" + lat);
		latLonSet.add("lon:" + lon);
		latLonSet.add(address);
		
		SharedPreferences.Editor pinnedPlacesFileEditor = pinnedPlacesFile.edit();
		pinnedPlacesFileEditor.putStringSet(name, latLonSet);
		pinnedPlacesFileEditor.commit();
		
		++pinnedPlacesCount;
		
	}
	
	public int getPinnedPlacesCount(){
		return pinnedPlacesCount;
	}
	
	public LatLonAddress getLocation(String name){
		double [] latLon = new double[2];
		Set<String> latLonSet = new HashSet<String>();
		LatLonAddress latLonAddr = new LatLonAddress();
		
		latLonSet = pinnedPlacesFile.getStringSet(name, latLonSet);
		for(String locVal : latLonSet){
			
			if(locVal == null)
				continue;
			
			if(locVal.contains("lat"))
				latLonAddr.setLat(Double.parseDouble(locVal.split(":")[1]));
			else if(locVal.contains("lon"))
				latLonAddr.setLon(Double.parseDouble(locVal.split(":")[1]));
			else 
				latLonAddr.setAddress(locVal);
		}
		
		return latLonAddr;
	}
	
	public void deletePinnedPlace(String name){
		
		SharedPreferences.Editor pinnedPlacesFileEditor = pinnedPlacesFile.edit();
		pinnedPlacesFileEditor.remove(name);
		pinnedPlacesFileEditor.commit();
		
		--pinnedPlacesCount;
	}
	
	public class LatLonAddress {
		double [] latLon;
		String address;
		
		public LatLonAddress(){
			
			latLon = new double[2];
		}
		
		public void setLat(double lat){
			latLon[0] = lat; 
		}
		
		public void setLon(double lon){
			latLon[1] = lon; 
		}

		public double[] getLatLon() {
			return latLon;
		}

		public void setLatLon(double[] latLon) {
			this.latLon = latLon;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}
	}
}
