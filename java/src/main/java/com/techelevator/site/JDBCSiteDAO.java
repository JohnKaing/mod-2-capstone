package com.techelevator.site;

import java.sql.Date;
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

	public List<Site> getAvailableSites(int campgroundId, Date arrivalDate, Date departureDate) {
		List<Site> sites = new ArrayList<>();

		String sqlAvailableSites =
				 "SELECT *\n" + 
				 "FROM site\n" + 
				 "WHERE campground_id = ?\n" + 
				 "AND\n" + 
				 "((site.site_id NOT IN (SELECT site_id FROM reservation))\n" + 
				 "OR\n" + 
				 "(site.site_id NOT IN \n" + 
				 "        (SELECT site_id FROM reservation WHERE (\n" + 
				 "         reservation.from_date BETWEEN ? AND ?\n" + 
				 "         AND\n" + 
				 "         reservation.to_date BETWEEN ? AND ?)\n" + 
				 "         OR\n" + 
				 "         (? BETWEEN reservation.from_date AND reservation.to_date)\n" + 
				 "         AND\n" + 
				 "         (? BETWEEN reservation.from_date AND reservation.to_date))))\n" + 
				 "LIMIT 5;"; 

		SqlRowSet results = myJdbcTemplate.queryForRowSet
				(sqlAvailableSites, campgroundId, arrivalDate, departureDate, arrivalDate, departureDate, arrivalDate, departureDate);
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
