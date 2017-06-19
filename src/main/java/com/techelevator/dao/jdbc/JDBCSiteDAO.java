package com.techelevator.dao.jdbc;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.techelevator.dao.SiteDAO;
import com.techelevator.park.Campground;
import com.techelevator.park.Site;

public class JDBCSiteDAO implements SiteDAO {

	private JdbcTemplate template;

	public JDBCSiteDAO(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Site> getAvailableSites(Campground campground, LocalDate fromDate, LocalDate toDate) {
		//Create select statement to query the database table site for campsites that are not currently booked for the dates and campsite defined by the user.
		List<Site> sites = new ArrayList<>();
		String sqlGetAvailableSites = "SELECT * FROM site WHERE campground_id = ? AND site_id NOT IN ("
				+ "SELECT site.site_id FROM reservation JOIN site ON site.site_id = reservation.site_id "
				+ "JOIN campground ON campground.campground_id = site.campground_id "
				+ "WHERE from_date <= ? AND to_date >= ?) LIMIT 5";
		//Convert the LocalDate variables passed-in to Dates and then query the database and save each campsite in the results into a List of campsites.
		Date sqlFromDate = Date.valueOf(fromDate);
		Date sqlToDate = Date.valueOf(toDate);
		SqlRowSet results = template.queryForRowSet(sqlGetAvailableSites, campground.getCampgroundId(), sqlFromDate, sqlToDate);
		while (results.next()) {
			Site theSite = mapRowToSite(results);
			sites.add(theSite);
		}
		return sites;
	}
	//Create a new campsite and set its values equal to the corresponding variables in the row returned by the query.
	private Site mapRowToSite(SqlRowSet results) {
		
		Site theSite;
		theSite = new Site();
		theSite.setSiteId(results.getLong("site_id"));
		theSite.setCampgroundId(results.getLong("campground_id"));
		theSite.setSiteNumber(results.getLong("site_number"));
		theSite.setMaxOccupancy(results.getLong("max_occupancy"));
		theSite.setAccessible(results.getBoolean("accessible"));
		theSite.setMaxRvLength(results.getLong("max_rv_Length"));
		theSite.setUtilities(results.getBoolean("utilities"));
		return theSite;
	}
}


