package com.techelevator.park;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate myJdbcTemplate;

	public JDBCParkDAO(DataSource aDataSource) {
		this.myJdbcTemplate = new JdbcTemplate(aDataSource); // instantiate and initialize JdbcTemplate with datasource
	}

	@Override
	public void save(Park savePark) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Park deletePark) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Park updatePark) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Park> getAllParks() {
		List<Park> allParks = new ArrayList<Park>(); // define result

		String sqlAllParks = "SELECT * FROM park";

		SqlRowSet results = myJdbcTemplate.queryForRowSet(sqlAllParks);

		while (results.next()) {
			Park thePark = mapRowToPark(results);
			allParks.add(thePark);
		}

		return allParks;
	}

	@Override
	public void create(Park createPark) {
		// TODO Auto-generated method stub

	}

	private Park mapRowToPark(SqlRowSet results) {
		Park thePark = new Park();

		thePark.setParkId(results.getInt("park_id"));
		thePark.setName(results.getString("name"));
		thePark.setLocation(results.getString("location"));
		thePark.setEstablishDate(results.getDate("establish_date"));
		thePark.setArea(results.getInt("area"));
		thePark.setVisitors(results.getInt("visitors"));
		thePark.setDescription(results.getString("description"));

		return thePark;

	}

}
