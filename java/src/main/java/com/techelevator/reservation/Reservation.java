package com.techelevator.reservation;

import java.sql.Date;

public class Reservation {

	private int reservation_id;
	private int site_id;
	private String name;
	private Date from_date;
	private Date to_date;
	private Date create_date; // when reservation is created by user?

	public Reservation() {
	}
	
	public Reservation(int siteId, String name, Date fromDate, Date toDate) {
//		this.reservation_id = reservationId;
		this.site_id = siteId;
		this.name = name;
		this.from_date = fromDate;
		this.to_date = toDate;
//		this.create_date = new Date(System.currentTimeMillis());
	}

	public int getReservation_id() {
		return reservation_id;
	}

	public void setReservation_id(int reservation_id) {
		this.reservation_id = reservation_id;
	}

	public int getSite_id() {
		return site_id;
	}

	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getFrom_date() {
		return from_date;
	}

	public void setFrom_date(Date from_date) {
		this.from_date = from_date;
	}

	public Date getTo_date() {
		return to_date;
	}

	public void setTo_date(Date to_date) {
		this.to_date = to_date;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

}
