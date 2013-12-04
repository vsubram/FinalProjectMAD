package edu.uncc.mad.huduku.location;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import edu.uncc.mad.huduku.observer.LocationChangeObserver;

public class LocationHelper {

	private static LocationManager locationManager;
	private static LocationChangeObserver observer;
	private static Location lastKnownLocation;
	private static LocationListener locationListener;
	
	static {
		locationListener = new MyLocationListener();
	}
	
	public LocationHelper(LocationManager loacManager){
		LocationHelper.locationManager = loacManager;
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
		lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}
	
	public static void setLocationManager(LocationManager lm){
		if(locationManager != null)
			LocationHelper.locationManager.removeUpdates(locationListener);
		LocationHelper.locationManager = lm;
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
		lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}

	public static double [] getCurrentLocation(){
		lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		double [] location = new double[2];
		location[0] = lastKnownLocation.getLatitude();
		location[1] = lastKnownLocation.getLongitude();
		
		return location;
	}
	
	/**
	 * Method from the LocationObservable interface. To register the locationChangeObserver. 
	 */
	public static void registerLocationObserver(LocationChangeObserver observer) {
		LocationHelper.observer = null;
		LocationHelper.observer = observer;
	}

	/**
	 * Method from the LocationObservable interface. Used to notify the LocationChangeListener about
	 * the location change. 
	 */
	public static void notifyObserver(double latitude, double longitude) {
		if(observer == null)
			return;
		
		observer.onLocationChanged(latitude, longitude);
		lastKnownLocation.setLatitude(latitude);
		lastKnownLocation.setLatitude(longitude);
		
	}
	
	/**
	 * This method calculates the distance between two points on earth in meters. The points are represented
	 * as a latitude-longitude pair. 
	 * @param lat1 Latitude of first point
	 * @param lng1 Longitude of first point
	 * @param lat2 Latitude of second point
	 * @param lng2 Longitude of second point
	 * @return distance between them in meters. 
	 */
	@SuppressWarnings("unused")
	private double distFrom(double lat1, double lng1, double lat2, double lng2) {
		
		double earthRadius = 3958.75;
		
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		int meterConversion = 1609;

		return dist * meterConversion;
	}
	
	private static class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			if(observer == null)
				return;
			
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			
			observer.onLocationChanged(latitude, longitude);
			
			if(lastKnownLocation == null)
				lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			
			lastKnownLocation.setLatitude(latitude);
			lastKnownLocation.setLatitude(longitude);
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
		
	}
}
