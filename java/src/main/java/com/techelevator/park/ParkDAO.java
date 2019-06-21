package com.techelevator.park;

import java.util.List;

public interface ParkDAO {

	public void save(Park savePark);

	public void delete(Park deletePark);

	public void update(Park updatePark);

	public List<Park> getAllParks();

	public void create(Park createPark);

	public Park getParkInfo(int id);

}
