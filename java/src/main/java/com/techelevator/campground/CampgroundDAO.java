package com.techelevator.campground;

import java.util.List;

public interface CampgroundDAO {

	public List<Campground> getCampgroundByParkId(int id);
	
	public void save(Campground saveCampground);

	public void delete(Campground deleteCampground);

	public void update(Campground updateCampground);

	public List<Campground> getAllCampground();

	public void create(Campground createCampground);

}
