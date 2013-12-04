package edu.uncc.mad.huduku.observer;

public interface LocationObservable {
	void registerLocationObserver(LocationChangeObserver observer);
	void notifyObserver(double latitude, double longitude);
	double [] getCurrentLocation();
}
