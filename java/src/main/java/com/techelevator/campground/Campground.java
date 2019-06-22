package com.techelevator.campground;

import java.text.DecimalFormat;

public class Campground {

	private int    campgroundId;
	private int    parkId;
	private String name;
	private String openFromMm;
	private String openToMm;
	private double dailyFee;
	/**
	 * @return the campgroundId
	 */
	public int getCampgroundId() {
		return campgroundId;
	}
	/**
	 * @param campgroundId the campgroundId to set
	 */
	public void setCampgroundId(int campgroundId) {
		this.campgroundId = campgroundId;
	}
	/**
	 * @return the parkId
	 */
	public int getParkId() {
		return parkId;
	}
	/**
	 * @param parkId the parkId to set
	 */
	public void setParkId(int parkId) {
		this.parkId = parkId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the openFromMm
	 */
	public String getOpenFromMm() {
		return openFromMm;
	}
	/**
	 * @param openFromMm the openFromMm to set
	 */
	public void setOpenFromMm(String openFromMm) {
		this.openFromMm = openFromMm;
	}
	/**
	 * @return the openToMm
	 */
	public String getOpenToMm() {
		return openToMm;
	}
	/**
	 * @param openToMm the openToMm to set
	 */
	public void setOpenToMm(String openToMm) {
		this.openToMm = openToMm;
	}
	/**
	 * @return the dailyFee
	 */
	public double getDailyFee() {
		return dailyFee;
	}
	/**
	 * @param dailyFee the dailyFee to set
	 */
	public void setDailyFee(double dailyFee) {
		this.dailyFee = dailyFee;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		DecimalFormat ft = new DecimalFormat("####");
		ft = new DecimalFormat("###,###.00"); 
		return "#" + campgroundId + "\t" + name + " \t" +  openFromMm + "\t" +  openToMm + "\t" + "$" + ft.format(dailyFee);		// TODO fix formatting
	}
	
	
	
	
}
