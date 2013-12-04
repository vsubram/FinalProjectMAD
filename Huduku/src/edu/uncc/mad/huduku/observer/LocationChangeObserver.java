package edu.uncc.mad.huduku.observer;

public interface LocationChangeObserver {
	void onLocationChanged(double latitude, double longitude);
	void registerWithLocationChangeObservable(LocationObservable locationChangeObservable);
}
