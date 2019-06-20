package com.techelevator;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.view.Menu;

public class CampgroundApp {

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

//		CampgroundCLI application = new CampgroundCLI(dataSource);
//		application.run();

		Menu appMenu = new Menu(System.in, System.out); // Instantiate a menu for Vending Machine to use
		CampgroundCLI campgroundCli = new CampgroundCLI(appMenu); // Instantiate a Vending Machine CLI passing it the
																	// Menu object to use
		campgroundCli.run(); // Tell the Vending MachineCLI to start running
	}
}
