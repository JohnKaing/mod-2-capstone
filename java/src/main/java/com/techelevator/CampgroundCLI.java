package com.techelevator;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
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

	private static final String CAMPGROUND_MENU_OPTION_VIEW_CAMPGROUNDS = "View Campgrounds";
	private static final String CAMPGROUND_MENU_OPTION_EXIT = "Return to Previous Screen";
	private static final String[] CAMPGROUND_MENU_OPTIONS = { CAMPGROUND_MENU_OPTION_VIEW_CAMPGROUNDS,
			CAMPGROUND_MENU_OPTION_EXIT };

	private static final String CAMPGROUND_SUBMENU_OPTION_SEARCH = "Search For Reservation Availability";
	private static final String CAMPGROUND_SUBMENU_OPTION_EXIT = "Return to Main Menu";
	private static final String[] CAMPGROUND_SUBMENU_OPTIONS = { CAMPGROUND_SUBMENU_OPTION_SEARCH,
			CAMPGROUND_SUBMENU_OPTION_EXIT };

	private Menu menu; // Menu object to be used by an instance of this class

	public CampgroundCLI(Menu Menu) { // Constructor - user will pas a menu for this class to use
		this.menu = Menu; // Make the Menu the user object passed, our Menu
	}

	public static void main(String[] args) {

		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		Menu appMenu = new Menu(System.in, System.out);
		CampgroundCLI application = new CampgroundCLI(appMenu);
		application.run();
	}

	// BEGIN MAIN PROGRAM

	public void run() {
		boolean shouldProcess = true; // Loop control variable
		while (shouldProcess) { // Loop until user indicates they want to exit

			mainParkMenu();
		}
		return; // End method and return to caller
	}

	private void mainParkMenu() {
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

		String choice = (String) menu.getChoiceFromOptions(menuParkNames); // Display menu and get
																			// choice

		for (int i = 0; i < menuParkNames.length; i++) {
			if (choice == "Quit") {
				System.out.println("Thank you, goodbye!");
				return;
			}
			if (choice == menuParkNames[i]) {
				System.out.println();
				System.out.println(parkDAO.getParkInfo(i + 1).toString());

				printHeading("Select a Command");
				String campChoice = (String) menu.getChoiceFromOptions(CAMPGROUND_MENU_OPTIONS);

				switch (campChoice) {

				case CAMPGROUND_MENU_OPTION_VIEW_CAMPGROUNDS:
					viewCampgrounds(i + 1);
					break;

				case CAMPGROUND_MENU_OPTION_EXIT:
					break;
				}
			}
		}
	}

	public void viewCampgrounds(int parkID) {
		printHeading("#\t Name \t\tOpen \tClose \tDaily Fee                   "); // TODO fix formatting

		HashMap<Integer, Double> campgroundIdFeeMap = handleCampgroundsAndFees(parkID);

		String choice = (String) menu.getChoiceFromOptions(CAMPGROUND_SUBMENU_OPTIONS);

		switch (choice) {

		case CAMPGROUND_SUBMENU_OPTION_SEARCH:
			try {
				// begin selecting available sites
				System.out.print("Which campground # would you like to select? (enter 0 to cancel) ");
				int userCampground = Integer.parseInt(keyboard.nextLine());

				if (userCampground == 0) {
					return;
				} else if (campgroundIdFeeMap.containsKey(userCampground)) {

					System.out.print("What is the arrival date? (YYYY-MM-DD): ");
					Date arrivalDate = Date.valueOf(keyboard.nextLine());
					System.out.print("What is the departure date? (YYYY-MM-DD): ");
					Date departureDate = Date.valueOf(keyboard.nextLine());

					if (departureDate.compareTo(arrivalDate) >= 0) {
						double dailyFee = campgroundIdFeeMap.get(userCampground);
						calculateDays(dailyFee, arrivalDate, departureDate); // calculate/print # of days and cost
						int chosenSiteId = handleAvailableSites // get available sites, return chosen site ID
						(userCampground, arrivalDate, departureDate);
						makeReservation(chosenSiteId, arrivalDate, departureDate);
					} else {
						System.out.println("\nSorry, it seems you are attempting time travel."
								+ " Please try again with valid dates!");
					}

				} else {
					System.out.println("Sorry, that isn't a valid campground. Please try again!");
				}

			} catch (Exception e) {
				System.out.println("\nSorry, there seems to have been an error. Please try again!");
			}

		case CAMPGROUND_SUBMENU_OPTION_EXIT:
			break;
		}
	}

	private HashMap<Integer, Double> handleCampgroundsAndFees(int parkID) {
		List<Campground> campgroundsById = campgroundDAO.getCampgroundByParkId(parkID);
		HashMap<Integer, Double> campgroundIdFeeMap = new HashMap<Integer, Double>();

		for (int i = 0; i < campgroundsById.size(); i++) {
			Campground current = campgroundsById.get(i);
			System.out.println(current.toString());
			campgroundIdFeeMap.put(current.getCampgroundId(), current.getDailyFee());
		}
		return campgroundIdFeeMap;
	}

	private void calculateDays(double dailyFee, Date arrivalDate, Date departureDate) {
		DecimalFormat ft = new DecimalFormat("####");
		ft = new DecimalFormat("###,###.00");

		int daysdiff = 0;
		long diff = arrivalDate.getTime() - departureDate.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000) + 1;
		daysdiff = (int) -(diffDays - 1);
		double totalCost = daysdiff * dailyFee;

		System.out.println("\n" + daysdiff + "  total days");
		System.out.println("$" + ft.format(dailyFee) + "  price per day");
		System.out.println("$" + ft.format(totalCost) + "  total cost");
	}

	private int handleAvailableSites(int userCampground, Date arrivalDate, Date departureDate) {
		System.out.println("\nResults Matching Your Search Criteria");
		printHeading("Site No.\tMax Occup.\tAccessible? \t Max RV Length \t Utilities?"); //

		List<Site> availableSites = siteDAO.getAvailableSites(userCampground, arrivalDate, departureDate);

		Site[] availableSiteArray = new Site[availableSites.size()];

		for (int i = 0; i < availableSites.size(); i++) {
			availableSiteArray[i] = availableSites.get(i);
		}

		Site chosenSite = (Site) menu.getChoiceFromOptions(availableSiteArray); // Display menu & get choice

		return chosenSite.getSite_id();
	}

	private void makeReservation(int chosenSiteId, Date arrivalDate, Date departureDate) {
		System.out.print("\nWhat name should the reservation be made under? ");
		String customerName = keyboard.nextLine();

		Reservation customerRes = new Reservation(chosenSiteId, customerName, arrivalDate, departureDate);

		int customerResId = reservationDAO.createReservation(customerRes);
		System.out.println("\nThanks! Your reservation number is " + customerResId + ". Enjoy your visit!");
	}

	private void printHeading(String headingText) {
		System.out.println("\n" + headingText);
		for (int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}

}
