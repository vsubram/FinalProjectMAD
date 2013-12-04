package edu.uncc.mad.huduku.core;

import android.graphics.Bitmap;

public class HomeScreen {
	
	private String reviewId;
	private Bitmap reviewImage;
	
	public HomeScreen(String reviewIdStr, Bitmap reviewImage){
		
		this.reviewId = reviewIdStr;
		this.reviewImage = reviewImage;
	}
	
	public String getReviewId() {
		return reviewId;
	}
	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}
	public Bitmap getReviewImage() {
		return reviewImage;
	}
	public void setReviewImage(Bitmap reviewImage) {
		this.reviewImage = reviewImage;
	}
	
	
}
