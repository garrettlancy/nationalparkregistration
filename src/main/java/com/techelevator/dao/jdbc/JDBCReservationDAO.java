package com.techelevator.dao.jdbc;

import java.sql.Date;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.techelevator.dao.ReservationDAO;
import com.techelevator.park.Reservation;

public class JDBCReservationDAO implements ReservationDAO {
	
	private JdbcTemplate template;

	public JDBCReservationDAO(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}
	
	@Override
	public long makeReservation(Reservation reservation) {
		//Add reservation information, provided by the user, to the database reservation table with an insert statement.
		String sqlMakeReservation = "INSERT INTO reservation (site_id, name, from_date, to_date, create_date) "
				+ "VALUES (?, ?, ?, ?, ?) ";
		template.update(sqlMakeReservation, reservation.getSiteId(), reservation.getName(),
				Date.valueOf(reservation.getFromDate()), Date.valueOf(reservation.getToDate()),
				Date.valueOf(reservation.getCreateDate()));
		//Query the database table reservation for the reservation that was just inserted and returns the reservation_id AKA "confirmation Id".
		String sqlGetConfirmationID = "SELECT reservation_id FROM reservation WHERE site_id = ? AND name = ? "
				+ "AND from_date = ? AND to_date = ? AND create_date = ?";
		SqlRowSet results = template.queryForRowSet(sqlGetConfirmationID, reservation.getSiteId(),
				reservation.getName(), reservation.getFromDate(), reservation.getToDate(), reservation.getCreateDate());
		results.next();
		Reservation theReservation = mapRowToReservation(results);
		
		return theReservation.getReservationId();
	}
	
	private Reservation mapRowToReservation(SqlRowSet results) {
		//Create a reservation object and set its reservation_id to reflect the reservation_id from the select statement results.
		Reservation theReservation;
		theReservation = new Reservation();
		theReservation.setReservationId(results.getLong("reservation_id"));

		return theReservation;
	}
}
	

