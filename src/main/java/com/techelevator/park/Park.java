package com.techelevator.park;

import java.time.LocalDate;

public class Park {

	private Long parkId;
	private String name;
	private String location;
	private LocalDate establishDate;
	private Long area;
	private Long visitors;
	private String description;

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDate getEstablishDate() {
		return establishDate;
	}

	public void setEstablishDate(LocalDate establishDate) {
		this.establishDate = establishDate;
	}

	public Long getArea() {
		return area;
	}

	public void setArea(Long area) {
		this.area = area;
	}

	public Long getVisitors() {
		return visitors;
	}

	public void setVisitors(Long visitors) {
		this.visitors = visitors;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return name;
	}

	public String getParkDetails() {

		String output = "";

		output += "\n" + name + "National Park\n\n";
		output += String.format("%-20s", "Location: ") + location + "\n";
		output += String.format("%-20s", "Established: ") + establishDate + "\n";
		output += String.format("%-20s", "Area: ") + String.format("%,d", area) + " sq km \n";
		output += String.format("%-20s", "Annual Visitors: ") + String.format("%,d", visitors) + "\n";

		return output;
	}
}
