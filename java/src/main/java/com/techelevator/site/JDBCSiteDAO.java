package com.techelevator.site;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCSiteDAO implements SiteDAO {

	private JdbcTemplate myJdbcTemplate;

	public JDBCSiteDAO(DataSource aDataSource) {
		this.myJdbcTemplate = new JdbcTemplate(aDataSource); // instantiate and initialize JdbcTemplate with datasource
	}

	public List<Site> getAvailableSites(int campgroundId, String arrivalDate, String departureDate) {
		List<Site> sites = new ArrayList<>();

		String sqlAvailableSites =
				"select * " + 
				"from site " + 
				"inner join " + 
				"campground " + 
				"on site.campground_id = campground.campground_id " + 
				"where site.site_id not in (select site_id from reservation) " + 
				"and site.campground_id = ? " + 
				"and site.site_id not in ( select site_id from reservation where " + 
				"reservation.from_date NOT between date(?) and date(?) " + 
				"AND " + 
				"reservation.to_date NOT between date(?) and date(?)) limit 5 "; 

		SqlRowSet results = myJdbcTemplate.queryForRowSet(sqlAvailableSites, campgroundId, arrivalDate, departureDate, arrivalDate, departureDate);
		//

		while (results.next()) {
			Site theSite = mapRowToSite(results);
			sites.add(theSite);
		}

		return sites;
	}

	@Override
	public Site createSite(Site newSite) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Site> returnAllSites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSite(Site updatedSite) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean deleteSite(Site siteToDelete) {
		// TODO Auto-generated method stub
		return false;
	}

	private Site mapRowToSite(SqlRowSet results) {
		Site theSite = new Site();

		theSite.setSite_id(results.getInt("site_id"));
		theSite.setCampground_id(results.getInt("campground_id"));
		theSite.setSite_number(results.getInt("site_number"));
		theSite.setMax_occupancy(results.getInt("max_occupancy"));
		theSite.setAccessible(results.getBoolean("accessible"));
		theSite.setMax_rv_length(results.getInt("max_rv_length"));
		theSite.setUtilities(results.getBoolean("utilities"));

		return theSite;
	}

}
