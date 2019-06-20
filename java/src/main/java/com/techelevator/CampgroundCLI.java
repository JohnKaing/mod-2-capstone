package com.techelevator;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.CampgroundDAO;
import com.techelevator.campground.JDBCCampgroundDAO;
import com.techelevator.park.JDBCParkDAO;
import com.techelevator.park.Park;
import com.techelevator.park.ParkDAO;
import com.techelevator.projects.model.Department;
import com.techelevator.reservation.JDBCReservationDAO;
import com.techelevator.reservation.ReservationDAO;
import com.techelevator.site.JDBCSiteDAO;
import com.techelevator.site.SiteDAO;
import com.techelevator.view.Menu;

public class CampgroundCLI {

	private static final String MAIN_MENU_OPTION_VIEW_CAMPGROUNDS = "View Campgrounds";
	private static final String MAIN_MENU_OPTION_SEARCH = "Search For Reservation";
	private static final String MAIN_MENU_OPTION_EXIT = "Quit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_CAMPGROUNDS, MAIN_MENU_OPTION_SEARCH,
			MAIN_MENU_OPTION_EXIT };

	private Menu campgroundMenu; // Menu object to be used by an instance of this class
//	private JDBCParkDAO parkDAO;
	private JDBCReservationDAO reservationDAO;
	private JDBCCampgroundDAO campgroundDAO;
	private JDBCSiteDAO siteDAO;

	public CampgroundCLI(Menu Menu) { // Constructor - user will pas a menu for this class to use
		this.campgroundMenu = Menu; // Make the Menu the user object passed, our Menu
	}

	public CampgroundCLI(DataSource datasource) {

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		JDBCParkDAO parkDAO = new JDBCParkDAO(dataSource);
		reservationDAO = new JDBCReservationDAO(dataSource);
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		siteDAO = new JDBCSiteDAO(dataSource);

	}

	public void run() {
		
		CampgroundCLI campgroundCli
//		Menu appMenu = new Menu(System.in, System.out); // Instantiate a menu for Vending Machine to use

		// -------

		boolean shouldProcess = true; // Loop control variable

		while (shouldProcess) { // Loop until user indicates they want to exit

			printHeading("Select a Park for further details");
//			List<Park> allParks = parkDAO.getAllParks();
//			listParks(allParks);
			
			System.out.println(parkDAO.getAllParks());

			String choice = (String) campgroundMenu.getChoiceFromOptions(MAIN_MENU_OPTIONS); // Display menu and get
																								// choice

			switch (choice) { // Process based on user menu choice

			case MAIN_MENU_OPTION_VIEW_CAMPGROUNDS:
				viewCampgrounds();
				break; // Exit switch statement

			case MAIN_MENU_OPTION_SEARCH:
				break; // Exit switch statement

			case MAIN_MENU_OPTION_EXIT:
				shouldProcess = false; // Set variable to end loop
				break; // Exit switch statement
			}
		}
		return; // End method and return to caller
	}

	public void viewCampgrounds() {
		System.out.println("Campgrounds at selected Park:");
	}

	public void searchForReservations() {
		System.out.println("Which campground (enter 0 to cancel)?");
		System.out.println("What is the arrival date? (YYYY/MM/DD)");

	}

	
	private void listParks(List<Park> parks) {
		System.out.println();
		if(parks.size() > 0) {
			for(Park park : parks) {
				System.out.println(park.getName());
			}
		} else {
			System.out.println("\n*** No results ***");
		}
	}

	private void printHeading(String headingText) {
		System.out.println("\n" + headingText);
		for (int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}

}
