package com.techelevator.dao;

import java.util.List;
import com.techelevator.park.Campground;
import com.techelevator.park.Park;

public interface CampgroundDAO {
	
	public List<Campground> getAllFromCampground(Park park);

}
