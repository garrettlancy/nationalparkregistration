package com.techelevator.dao.jdbc;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.techelevator.dao.ParkDAO;
import com.techelevator.park.Park;

public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate template;

	public JDBCParkDAO(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Park> getAllFromPark() {
		//Query the database for all columns in the park table, then stores each row in results as a park object in a list.
		List<Park> parks = new ArrayList<>();
		String sqlgetAllFromPark = "SELECT * FROM park";
		SqlRowSet results = template.queryForRowSet(sqlgetAllFromPark);
		while (results.next()) {
			Park thePark = mapRowToPark(results);
			parks.add(thePark);
		}

		return parks;
	}

	private Park mapRowToPark(SqlRowSet results) {
		//Sets the attributes of the new park object equal to the corresponding attribute of the database table row and returns the park object.
		Park thePark;
		thePark = new Park();
		thePark.setParkId(results.getLong("park_id"));
		thePark.setName(results.getString("name"));
		thePark.setLocation(results.getString("location"));
		if (results.getDate("establish_date") != null) {
			thePark.setEstablishDate(results.getDate("establish_date").toLocalDate());
		}
		thePark.setArea(results.getLong("area"));
		thePark.setVisitors(results.getLong("visitors"));
		thePark.setDescription(results.getString("description"));

		return thePark;
	}
}


