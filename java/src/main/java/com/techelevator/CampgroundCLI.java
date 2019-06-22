package com.techelevator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.Campground;
import com.techelevator.campground.JDBCCampgroundDAO;
import com.techelevator.park.JDBCParkDAO;
import com.techelevator.park.Park;
import com.techelevator.reservation.JDBCReservationDAO;
import com.techelevator.reservation.Reservation;
import com.techelevator.site.JDBCSiteDAO;
import com.techelevator.site.Site;
import com.techelevator.view.Menu;

public class CampgroundCLI {

	private Scanner keyboard = new Scanner(System.in);

	static BasicDataSource dataSource = new BasicDataSource();

	static JDBCParkDAO parkDAO = new JDBCParkDAO(dataSource);
	static JDBCReservationDAO reservationDAO = new JDBCReservationDAO(dataSource);
	static JDBCCampgroundDAO campgroundDAO = new JDBCCampgroundDAO(dataSource);
	static JDBCSiteDAO siteDAO = new JDBCSiteDAO(dataSource);

	public static void main(String[] args) {

		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		Menu appMenu = new Menu(System.in, System.out);
		CampgroundCLI application = new CampgroundCLI(appMenu);
		application.run();
	}

	private static final String CAMPGROUND_MENU_OPTION_VIEW_CAMPGROUNDS = "View Campgrounds";
//	private static final String CAMPGROUND_MENU_OPTION_SEARCH = "Search For Reservation";
	private static final String CAMPGROUND_MENU_OPTION_EXIT = "Return to Previous Screen";
	private static final String[] CAMPGROUND_MENU_OPTIONS = { CAMPGROUND_MENU_OPTION_VIEW_CAMPGROUNDS,
			// CAMPGROUND_MENU_OPTION_SEARCH,
			CAMPGROUND_MENU_OPTION_EXIT };

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

	public void run() {
		boolean shouldProcess = true; // Loop control variable

		while (shouldProcess) { // Loop until user indicates they want to exit

			printHeading("Select a Park for further details");
			List<Park> allParks = parkDAO.getAllParks();
			List<String> allParkNames = new ArrayList<String>();

			for (Park eachPark : allParks) {
				allParkNames.add(eachPark.getName());
			}

			String[] menuParkNames = new String[allParkNames.size() + 1];

			for (int i = 0; i < allParkNames.size(); i++) {
				menuParkNames[i] = allParkNames.get(i);
			}

			menuParkNames[menuParkNames.length - 1] = "Quit";

			String choice = (String) campgroundMenu.getChoiceFromOptions(menuParkNames); // Display menu and get
																							// choice

//			campgroundMenu.displayMenuOptions(MAIN_MENU_OPTIONS);

			for (int i = 0; i < menuParkNames.length; i++) {
				if (choice == "Quit") {
					System.out.println("Thank you, goodbye!");
					return;
				}
				if (choice == menuParkNames[i]) {
					System.out.println();
					System.out.println(parkDAO.getParkInfo(i + 1).toString());

					printHeading("Select a Command");
					String campChoice = (String) campgroundMenu.getChoiceFromOptions(CAMPGROUND_MENU_OPTIONS);

					switch (campChoice) {

					case CAMPGROUND_MENU_OPTION_VIEW_CAMPGROUNDS:
						viewCampgrounds(i + 1);
						break;

//					case CAMPGROUND_MENU_OPTION_SEARCH:
//						// SEARCH METHOD
//						break;

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

	public void viewCampgrounds(int parkID) {
		printHeading("\t Name \t\tOpen \tClose \tDaily Fee                   "); // TODO fix formatting

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
			try {
//			String campResChoice2 = (String) campgroundMenu.getChoiceFromOptions(campgroundStrings);
//			System.out.println(campResChoice2);

				// begin selecting available sites
				System.out.print("Which campground would you like to select? (enter 0 to cancel) ");
				int userCampground = Integer.parseInt(keyboard.nextLine());
				System.out.print("What is the arrival date? (YYYY-MM-DD): ");
				Date arrivalDate = Date.valueOf(keyboard.nextLine());
				System.out.print("What is the departure date? (YYYY-MM-DD): ");
				Date departureDate = Date.valueOf(keyboard.nextLine());

				System.out.println("\nResults Matching Your Search Criteria");
				printHeading(
						"Site No.\tMax Occup.\tAccessible? \t Max RV Length \t Utilities? \t Cost                   "); //

				List<Site> availableSites = siteDAO.getAvailableSites(userCampground, arrivalDate, departureDate);

				Site[] availableSiteArray = new Site[availableSites.size()];

				for (int i = 0; i < availableSites.size(); i++) {
					availableSiteArray[i] = availableSites.get(i);
				}

				Site chosenSite = (Site) campgroundMenu.getChoiceFromOptions(availableSiteArray); // Display menu
																									// and get

				int chosenSiteId = chosenSite.getSite_id();

				// end available sites
				// --------
				// --------
				// --------
				// begin reservation

				System.out.print("\nWhat name should the reservation be made under? ");
				String customerName = keyboard.nextLine();

				Reservation customerRes = new Reservation(chosenSiteId, customerName, arrivalDate, departureDate);

				int customerResId = reservationDAO.createReservation(customerRes);
				System.out.println("\nThanks! Your reservation number is " + customerResId + ". Enjoy your visit!");

			} catch (Exception e) {
				System.out.println("Sorry, there seems to have been an error. Please try again!");
			}

			// end reservation

		case CAMPGROUND_SUBMENU_OPTION_EXIT:
			break;
		}

	}

//	private List<Site> selectAvailableSites() {
//		System.out.print("Which campground would you like to select? (enter 0 to cancel) ");
//		int userCampground = Integer.parseInt(keyboard.nextLine());
//		System.out.print("What is the arrival date? (YYYY-MM-DD): ");
//		Date arrivalDate = Date.valueOf(keyboard.nextLine());
//		System.out.print("What is the departure date? (YYYY-MM-DD): ");
//		Date departureDate = Date.valueOf(keyboard.nextLine());
//
//		System.out.println("\nResults Matching Your Search Criteria");
//		printHeading("Site No. \t Max Occup. \t Accessible? \t Max RV Length \t Utilities? \t Cost"); //
//
//		return siteDAO.getAvailableSites(userCampground, arrivalDate, departureDate);
//	}

	private void printHeading(String headingText) {
		System.out.println("\n" + headingText);
		for (int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}
//
//	private void reserveSite(ReservationDAO reservationDAO) {
//		
//		System.out.print("\nWhich site should be reserved? ");
//		String customerSite = keyboard.nextLine();
//
//		
//		System.out.print("\nWhat name should the reservation be made under? ");
//		String customerName = keyboard.nextLine();
//
//	}

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
//
//	private void listParks(List<Park> parks) {
//		System.out.println();
//		if (parks.size() > 0) {
//			for (Park park : parks) {
//				System.out.println(park.getName());
//			}
//		} else {
//			System.out.println("\n*** No results ***");
//		}
//	}

}
