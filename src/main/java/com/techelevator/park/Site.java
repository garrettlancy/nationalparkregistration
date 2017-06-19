package com.techelevator.park;

import java.math.BigDecimal;

public class Site {

	private Long siteId;
	private Long campgroundId;
	private Long siteNumber;
	private Long maxOccupancy;
	private boolean accessible;
	private Long maxRvLength;
	private boolean utilities;

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Long getCampgroundId() {
		return campgroundId;
	}

	public void setCampgroundId(Long campgroundId) {
		this.campgroundId = campgroundId;
	}

	public Long getSiteNumber() {
		return siteNumber;
	}

	public void setSiteNumber(Long siteNumber) {
		this.siteNumber = siteNumber;
	}

	public Long getMaxOccupancy() {
		return maxOccupancy;
	}

	public void setMaxOccupancy(Long maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}

	public boolean getAccessible() {
		return accessible;
	}

	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}

	public Long getMaxRvLength() {
		return maxRvLength;
	}

	public void setMaxRvLength(Long maxRvLength) {
		this.maxRvLength = maxRvLength;
	}

	public boolean getUtilities() {
		return utilities;
	}

	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}

	public String toString() {

		return "Site ID #" + this.siteId;
	}

	public String getSiteDetails(BigDecimal cost) {
		
		String siteDetails = String.format("%-12s %-12s %-12s %-15s %-12s %-12s", siteNumber, maxOccupancy,
				accessible ? "Yes" : "No", (maxRvLength == 0) ? "N/A" : maxRvLength, utilities ? "Yes" : "N/A",
				"$" + String.format("%.2f", cost)) + "\n";

		return siteDetails;
	}
}
