package com.techelevator.site;

import java.util.List;

public interface SiteDAO {

	public Site createSite(Site newSite);

	public List<Site> returnAllSites();

	public void updateSite(Site updatedSite);

	public boolean deleteSite(Site siteToDelete);

}
