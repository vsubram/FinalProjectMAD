package edu.uncc.mad.huduku.core;

public class Review {

	private String reviewText;
	private double reviewRating;
	private long timePosted;
	private String userName;
	private String userImageUrl;
	private String authorURL;
	
	public Review(){
		
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public double getReviewRating() {
		return reviewRating;
	}

	public void setReviewRating(double reviewRating) {
		this.reviewRating = reviewRating;
	}

	public long getTimePosted() {
		return timePosted;
	}

	public void setTimePosted(long timePosted) {
		this.timePosted = timePosted;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserImageUrl() {
		return userImageUrl;
	}

	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}

	public String getAuthorURL() {
		return authorURL;
	}

	public void setAuthorURL(String authorURL) {
		this.authorURL = authorURL;
	}
}
