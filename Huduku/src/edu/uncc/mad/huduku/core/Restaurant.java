package edu.uncc.mad.huduku.core;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Restaurant implements Parcelable{
	
	private List<Deal> deals;
	private List<Review> reviews;
	private String name;
	private double rating;
	private String id; 
	private String url;
	private double [] location;
	private String address;
	
	public Restaurant(){
		location = new double[2];
	}
	
	public Restaurant(String id, String name, double rating){
		setId(id);
		setName(name);
		setRating(rating);
	}
	
	public List<Deal> getDeals() {
		return deals;
	}
	public void setDeals(List<Deal> deals) {
		this.deals = deals;
	}
	public List<Review> getReviews() {
		return reviews;
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int arg1) {
		parcel.writeList(deals);
		parcel.writeList(reviews);
		parcel.writeString(id);
		parcel.writeString(name);
		parcel.writeDouble(rating);
	}

	public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {
		public Restaurant createFromParcel(Parcel in) {
			return new Restaurant(in);
		}

		public Restaurant[] newArray(int size) {
			return new Restaurant[size];
		}
	};

	private Restaurant(Parcel in) {
		in.readList(deals, null);
		in.readList(reviews, null);
		
		id = in.readString();
		name = in.readString();
		rating = in.readDouble();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public double [] getLocation() {
		return location;
	}

	public void setLocation(double [] location) {
		this.location[0] = location[0];
		this.location[1] = location[1];
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
