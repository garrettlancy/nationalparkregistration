package com.techelevator.dao.jdbc;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.techelevator.dao.CampgroundDAO;
import com.techelevator.park.Campground;
import com.techelevator.park.Park;

public class JDBCCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate template;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Campground> getAllFromCampground(Park park) {
		//Query the database for all columns in the campground table for a specified park_id then stores each row in results as a campground object in a list.
		List<Campground> campgrounds = new ArrayList<>();
		String sqlGetAllFromCampground = "SELECT * FROM campground WHERE park_id=?";
		SqlRowSet results = template.queryForRowSet(sqlGetAllFromCampground, park.getParkId());
		while (results.next()) {
			Campground theCampground = mapRowToCampground(results);
			campgrounds.add(theCampground);
		}
		return campgrounds;
	}

	private Campground mapRowToCampground(SqlRowSet results) {
		//Sets the attributes of the new campground object equal to the corresponding attribute of the database table row and returns the campground object.
		Campground theCampground;
		theCampground = new Campground();
		theCampground.setCampgroundId(results.getLong("campground_id"));
		theCampground.setParkId(results.getLong("park_id"));
		theCampground.setName(results.getString("name"));
		theCampground.setOpenFromMonth(Integer.valueOf(results.getInt("open_from_mm")));
		theCampground.setOpenTo(Integer.valueOf(results.getInt("open_to_mm")));
		theCampground.setDailyFee(results.getBigDecimal("daily_fee"));

		return theCampground;
	}
}

