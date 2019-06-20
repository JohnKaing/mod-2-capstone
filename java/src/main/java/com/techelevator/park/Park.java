package com.techelevator.park;

import java.util.Date;

public class Park {

	private int    parkId;
	private String name;
	private String location;
	private Date   establishDate;
	private int    area;
	private int    visitors;
	private String description;
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
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the establishDate
	 */
	public Date getEstablishDate() {
		return establishDate;
	}
	/**
	 * @param establishDate the establishDate to set
	 */
	public void setEstablishDate(Date establishDate) {
		this.establishDate = establishDate;
	}
	/**
	 * @return the area
	 */
	public int getArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(int area) {
		this.area = area;
	}
	/**
	 * @return the visitors
	 */
	public int getVisitors() {
		return visitors;
	}
	/**
	 * @param visitors the visitors to set
	 */
	public void setVisitors(int visitors) {
		this.visitors = visitors;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Park [parkId=" + parkId + ", name=" + name + ", location=" + location + ", establishDate="
				+ establishDate + ", area=" + area + ", visitors=" + visitors + ", description=" + description + "]";
	}
	
	
}


