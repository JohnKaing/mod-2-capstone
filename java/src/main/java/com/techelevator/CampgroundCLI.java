package com.techelevator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.Campground;
import com.techelevator.campground.CampgroundDAO;
import com.techelevator.campground.JDBCCampgroundDAO;
import com.techelevator.park.JDBCParkDAO;
import com.techelevator.park.Park;
import com.techelevator.park.ParkDAO;
import com.techelevator.site.JDBCSiteDAO;
import com.techelevator.site.Site;
import com.techelevator.site.SiteDAO;
import com.techelevator.view.Menu;

public class CampgroundCLI {

	private Scanner keyboard = new Scanner(System.in);
	
	public static void main(String[] args) {

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		JDBCParkDAO parkDAO = new JDBCParkDAO(dataSource);
//		reservationDAO = new JDBCReservationDAO(dataSource);
		JDBCCampgroundDAO campgroundDAO = new JDBCCampgroundDAO(dataSource);
		JDBCSiteDAO siteDAO = new JDBCSiteDAO(dataSource);

		Menu appMenu = new Menu(System.in, System.out); 
		CampgroundCLI application = new CampgroundCLI(appMenu); 
		application.run(parkDAO, campgroundDAO, siteDAO); 
	}

	private static final String CAMPGROUND_MENU_OPTION_VIEW_CAMPGROUNDS = "View Campgrounds";
	private static final String CAMPGROUND_MENU_OPTION_SEARCH = "Search For Reservation";
	private static final String CAMPGROUND_MENU_OPTION_EXIT = "Return to Previous Screen";
	private static final String[] CAMPGROUND_MENU_OPTIONS = { CAMPGROUND_MENU_OPTION_VIEW_CAMPGROUNDS,
			CAMPGROUND_MENU_OPTION_SEARCH, CAMPGROUND_MENU_OPTION_EXIT };

	private static final String CAMPGROUND_SUBMENU_OPTION_SEARCH = "Search For Reservation";
	private static final String CAMPGROUND_SUBMENU_OPTION_EXIT = "Return to Main Menu";
	private static final String[] CAMPGROUND_SUBMENU_OPTIONS = { CAMPGROUND_SUBMENU_OPTION_SEARCH,
			CAMPGROUND_SUBMENU_OPTION_EXIT };

	// = { MAIN_MENU_OPTION_VIEW_CAMPGROUNDS, MAIN_MENU_OPTION_SEARCH,
	// MAIN_MENU_OPTION_EXIT };

	private Menu campgroundMenu; // Menu object to be used by an instance of this class

	public CampgroundCLI(Menu Menu) { // Constructor - user will pas a menu for this class to use
		this.campgroundMenu = Menu; // Make the Menu the user object passed, our Menu
	}

//	public CampgroundCLI(DataSource datasource) {
//
//	}

	public void run(ParkDAO parkDAO, CampgroundDAO campgroundDAO, SiteDAO siteDAO) {
		boolean shouldProcess = true; // Loop control variable

		while (shouldProcess) { // Loop until user indicates they want to exit

			printHeading("Select a Park for further details");
			List<Park> allParks = parkDAO.getAllParks();
			List<String> allParkNames = new ArrayList<String>();

			for (Park eachPark : allParks) {
				allParkNames.add(eachPark.getName());
			}

			String[] MAIN_MENU_OPTIONS = new String[allParkNames.size() + 1];

			for (int i = 0; i < allParkNames.size(); i++) {
				MAIN_MENU_OPTIONS[i] = allParkNames.get(i);
			}

			MAIN_MENU_OPTIONS[MAIN_MENU_OPTIONS.length - 1] = "Quit";

			// listParks(allParks);

//			System.out.println(parkDAO.getAllParks());

			String choice = (String) campgroundMenu.getChoiceFromOptions(MAIN_MENU_OPTIONS); // Display menu and get
																								// choice

//			campgroundMenu.displayMenuOptions(MAIN_MENU_OPTIONS);

			for (int i = 0; i < MAIN_MENU_OPTIONS.length; i++) {
				if (choice == "Quit") {
					System.out.println("Thank you, goodbye!");
					return;
				}
				if (choice == MAIN_MENU_OPTIONS[i]) {
					System.out.println();
					System.out.println(parkDAO.getParkInfo(i + 1).toString());

					printHeading("Select a Command");
					String campChoice = (String) campgroundMenu.getChoiceFromOptions(CAMPGROUND_MENU_OPTIONS);

					switch (campChoice) {

					case CAMPGROUND_MENU_OPTION_VIEW_CAMPGROUNDS:
						viewCampgrounds(campgroundDAO, i + 1, siteDAO);
						break;

					case CAMPGROUND_MENU_OPTION_SEARCH:
						// SEARCH METHOD
						break;

					case CAMPGROUND_MENU_OPTION_EXIT:
						break;
					}

				}
			}

//			switch (choice) { // Process based on user menu choice
//
//			case MAIN_MENU_OPTION_VIEW_CAMPGROUNDS:
//				viewCampgrounds();
//				break; // Exit switch statement
//
//			case MAIN_MENU_OPTION_SEARCH:
//				break; // Exit switch statement
//
//			case MAIN_MENU_OPTION_EXIT:
//				shouldProcess = false; // Set variable to end loop
//				break; // Exit switch statement
//			}
		}
		return; // End method and return to caller
	}

	public void viewCampgrounds(CampgroundDAO campgroundDAO, int parkID, SiteDAO siteDAO) {
		System.out.println("\t Name \t\t Open \t Close \t Daily Fee "); // TODO fix formatting

		List<Campground> campgroundsById = campgroundDAO.getCampgroundByParkId(parkID);
		for (int i = 0; i < campgroundsById.size(); i++) {
			System.out.println(campgroundsById.get(i).toString());
		}
		
//		String[] campgroundStrings = new String[campgroundsById.size()];
//	
//		for (int i = 0; i < campgroundsById.size(); i++) {
//			campgroundStrings[i] = campgroundsById.get(i).toString();
//		}
//		String campResChoice = (String) campgroundMenu.getChoiceFromOptions(campgroundStrings);
//		System.out.println(campResChoice);
//	

		String campSubChoice = (String) campgroundMenu.getChoiceFromOptions(CAMPGROUND_SUBMENU_OPTIONS);

		switch (campSubChoice) {

		case CAMPGROUND_SUBMENU_OPTION_SEARCH:
//			String campResChoice2 = (String) campgroundMenu.getChoiceFromOptions(campgroundStrings);
//			System.out.println(campResChoice2);
			
			System.out.println("Which campground would you like to select? (enter 0 to cancel) ");
			int userCampground = Integer.parseInt(keyboard.nextLine());
			System.out.println(userCampground);
			System.out.println("What is the arrival date? (YYYY-MM-DD)");
			String arrivalDate = keyboard.nextLine();
			System.out.println("What is the departure date? (YYYY-MM-DD)");
			String departureDate = keyboard.nextLine();
			
			List<Site> availableSites = siteDAO.getAvailableSites(1, "2019-05-01", "2019-05-02");
			
			for (int i = 0; i < availableSites.size(); i++) {
				System.out.println(availableSites.get(i).toString());
			}
			
			
			
			
		case CAMPGROUND_SUBMENU_OPTION_EXIT:
			break;
		}

	}

//	public void searchForReservations(int numOfOptions) {
//		System.out.println("Which campground would you like to select? (enter 0 to cancel) ");
//
//		
//		campgroundMenu.getNumberChoice(numOfOptions);
//		
//		
//		
//		
//		System.out.println("What is the arrival date? (YYYY/MM/DD)");
//
//	}

	private void listParks(List<Park> parks) {
		System.out.println();
		if (parks.size() > 0) {
			for (Park park : parks) {
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
