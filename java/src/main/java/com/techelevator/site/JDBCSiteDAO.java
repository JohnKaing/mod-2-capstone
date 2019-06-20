package com.techelevator.site;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCSiteDAO implements SiteDAO {
	
	private JdbcTemplate myJdbcTemplate;

	public JDBCSiteDAO(DataSource aDataSource) {
		this.myJdbcTemplate = new JdbcTemplate(aDataSource); // instantiate and initialize JdbcTemplate with datasource
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
