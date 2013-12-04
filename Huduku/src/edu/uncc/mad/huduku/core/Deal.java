package edu.uncc.mad.huduku.core;

public class Deal {
	
	private String dealDetails;
	private String title;
	private String buyUrl;
	private int remainingCount;
	private String originalPrice;
	private String newPrice;
	private String restrictions;
	
	public Deal(){
		
	}
	
	public Deal(String title, String buyUrl, String restrictions, String origPrice, String newPrice, int remainingCount){
		setTitle(title);
		setBuyUrl(buyUrl);
		setRestrictions(restrictions);
		setRemainingCount(remainingCount);
		setOriginalPrice(origPrice);
		setNewPrice(newPrice);
	}
	
	public String getDealDetails() {
		return dealDetails;
	}
	public void setDealDetails(String dealDetails) {
		this.dealDetails = dealDetails;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBuyUrl() {
		return buyUrl;
	}
	public void setBuyUrl(String buyUrl) {
		this.buyUrl = buyUrl;
	}
	public int getRemainingCount() {
		return remainingCount;
	}
	public void setRemainingCount(int remainingCount) {
		this.remainingCount = remainingCount;
	}
	public String getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}
	public String getNewPrice() {
		return newPrice;
	}
	public void setNewPrice(String newPrice) {
		this.newPrice = newPrice;
	}
	public String getRestrictions() {
		return restrictions;
	}
	public void setRestrictions(String restrictions) {
		this.restrictions = restrictions;
	}
}
