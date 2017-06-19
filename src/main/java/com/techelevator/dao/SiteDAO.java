package com.techelevator.dao;

import java.time.LocalDate;
import java.util.List;
import com.techelevator.park.Campground;
import com.techelevator.park.Site;

public interface SiteDAO {
	
	public List<Site> getAvailableSites(Campground campground, LocalDate arrivalDate, LocalDate departureDate);
}
