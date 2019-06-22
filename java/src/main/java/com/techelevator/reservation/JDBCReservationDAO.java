package com.techelevator.reservation;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate myJdbcTemplate;

	public JDBCReservationDAO(DataSource aDataSource) {
		this.myJdbcTemplate = new JdbcTemplate(aDataSource); // instantiate and initialize JdbcTemplate with datasource
	}

	@Override
	public int createReservation(Reservation newReservation) {
		int reservationId = 1234;

		String sqlInsertRes = "insert into reservation (site_id, name, from_date, to_date, create_date) values "
				+ "(?, ?, ?, ?, current_date)";

		myJdbcTemplate.update(sqlInsertRes, newReservation.getSite_id(), newReservation.getName(),
				newReservation.getFrom_date(), newReservation.getTo_date());

		SqlRowSet results = myJdbcTemplate.queryForRowSet("SELECT MAX(reservation_id) FROM reservation");

		while (results.next()) {
			reservationId = results.getInt(1);
		}

		return reservationId;
	}

	@Override
	public List<Reservation> returnAllReservations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateReservation(Reservation updatedReservation) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean deleteReservation(Reservation ReservationToDelete) {
		// TODO Auto-generated method stub
		return false;
	}

	private Reservation mapRowToReservation(SqlRowSet results) {
		Reservation theReservation = new Reservation();

		theReservation.setReservation_id(results.getInt("reservation_id"));
		theReservation.setSite_id(results.getInt("site_id"));
		theReservation.setName(results.getString("name"));
		theReservation.setFrom_date(results.getDate("from_date"));
		theReservation.setTo_date(results.getDate("to_date"));
		theReservation.setCreate_date(results.getDate("create_date"));

		return theReservation;
	}

	@Override
	public List<Reservation> getReservationsByDate(Date arrivalDate, Date departureDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
