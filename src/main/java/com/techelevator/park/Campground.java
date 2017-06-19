package com.techelevator.park;

import java.math.BigDecimal;

public class Campground {

	private Long campgroundId;
	private Long parkId;
	private String name;
	private int openFromMonth;
	private int openToMonth;
	private BigDecimal dailyFee;
	public static final String[] months = { "January", "February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };

	public Long getCampgroundId() {
		return campgroundId;
	}

	public void setCampgroundId(Long campgroundId) {
		this.campgroundId = campgroundId;
	}

	public Long getParkId() {
		return parkId;
	}

	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOpenFromMonth() {
		return openFromMonth;
	}

	public void setOpenFromMonth(int openFromMonth) {
		this.openFromMonth = openFromMonth;
	}

	public int getOpenToMonth() {
		return openToMonth;
	}

	public void setOpenTo(int openToMonth) {
		this.openToMonth = openToMonth;
	}

	public BigDecimal getDailyFee() {
		return dailyFee;
	}

	public void setDailyFee(BigDecimal dailyFee) {
		this.dailyFee = dailyFee;
	}

	public String toString() {
		return name;
	}
	 
	public String getCampgroundDetails() {
		String campgroundDetails = "";
		campgroundDetails += String.format("%-32s %-15s %-15s %-15s", name, months[openFromMonth - 1], months[openToMonth - 1], "$" + String.format("%.2f", dailyFee));
		return campgroundDetails;
	}
}
