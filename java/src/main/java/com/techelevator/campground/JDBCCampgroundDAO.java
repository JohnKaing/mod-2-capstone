package com.techelevator.campground;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.park.Park;

public class JDBCCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate myJdbcTemplate;

	public JDBCCampgroundDAO(DataSource aDataSource) {
		this.myJdbcTemplate = new JdbcTemplate(aDataSource); // instantiate and initialize JdbcTemplate with datasource
	}

	@Override
	public List<Campground> getCampgroundByParkId(int id) {
		List<Campground> campgroundList = new ArrayList<>();

		String sqlCampgroundSearch = "select * FROM campground WHERE park_id = ?";

		SqlRowSet results = myJdbcTemplate.queryForRowSet(sqlCampgroundSearch, id);

		while (results.next()) {
			Campground theCampground = mapRowToCampground(results);
			campgroundList.add(theCampground);
		}

		return campgroundList;
	}

	@Override
	public void save(Campground saveCampground) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Campground deleteCampground) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Campground updateCampground) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Campground> getAllCampground() {
		List<Campground> allCampgroundList = new ArrayList<>();

		String sqlAllCampground = "SELECT * FROM campground";

		SqlRowSet results = myJdbcTemplate.queryForRowSet(sqlAllCampground);

		while (results.next()) {
			Campground theCampground = mapRowToCampground(results);
			allCampgroundList.add(theCampground);
		}

		return allCampgroundList;
	}

	@Override
	public void create(Campground createCampground) {
		// TODO Auto-generated method stub

	}

	private Campground mapRowToCampground(SqlRowSet results) {
		Campground theCampground = new Campground();

		theCampground.setCampgroundId(results.getInt("campground_id"));
		theCampground.setParkId(results.getInt("park_id"));
		theCampground.setName(results.getString("name"));
		theCampground.setOpenFromMm(results.getString("open_from_mm"));
		theCampground.setOpenToMm(results.getString("open_to_mm"));
		theCampground.setDailyFee(results.getDouble("daily_fee"));

		return theCampground;
	}

}
