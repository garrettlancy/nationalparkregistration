package com.techelevator;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import com.techelevator.dao.CampgroundDAO;
import com.techelevator.dao.ParkDAO;
import com.techelevator.dao.ReservationDAO;
import com.techelevator.dao.SiteDAO;
import com.techelevator.dao.jdbc.JDBCCampgroundDAO;
import com.techelevator.dao.jdbc.JDBCParkDAO;
import com.techelevator.dao.jdbc.JDBCReservationDAO;
import com.techelevator.dao.jdbc.JDBCSiteDAO;
import com.techelevator.park.Campground;
import com.techelevator.park.Park;
import com.techelevator.park.Reservation;
import com.techelevator.park.Site;

public class CampgroundCLI {
	//Menu options stored in string variables to be inserted into methods and displayed by the menu.
	private static final String PARK_INFO_MENU_OPTION_VIEW_CAMPGROUNDS = "View Campgrounds";
	private static final String MENU_OPTION_SEARCH_FOR_AVAILABLE_RESERVATION = "Search for Available Reservation";
	private static final String MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN = "Return to Previous Screen";
	private static final String[] PARK_INFO_MENU_OPTIONS = new String[] {PARK_INFO_MENU_OPTION_VIEW_CAMPGROUNDS, MENU_OPTION_SEARCH_FOR_AVAILABLE_RESERVATION, MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN};
	private static final String CAMPGROUND_RESERVATION_MENU_ARRIVAL_DATE = "What is the arrival date? (MM/DD/YYYY)";
	private static final String CAMPGROUND_RESERVATION_MENU_DEPARTURE_DATE = "What is the departure date? (MM/DD/YYYY)";
	private static final String MAKE_RESERVATION_MENU_RESERVATION_NAME = "What name should the reservation be made under? ";
	private static final String[] CAMPGROUND_MENU_OPTIONS = new String[] {MENU_OPTION_SEARCH_FOR_AVAILABLE_RESERVATION, MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN};
	//Initialize variables and objects to be called upon.
	private ParkMenu parkMenu;
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private SiteDAO siteDAO;
	private ReservationDAO reservationDAO;

	public static void main(String[] args) {
		//Establish a connection to the database.
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/parks");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();

	}

	public CampgroundCLI(DataSource datasource) {
		
		parkDAO = new JDBCParkDAO(datasource);
		campgroundDAO = new JDBCCampgroundDAO(datasource);
		siteDAO = new JDBCSiteDAO(datasource);
		reservationDAO = new JDBCReservationDAO(datasource);
		this.parkMenu = new ParkMenu(System.in, System.out);

	}
	
	public void printHeading(String headingText) {
		
		//Creates a text heading from the string passed in, adding a row of characters underneath the length of the string.
		System.out.println("\n" + headingText);
		for (int i = 0; i < headingText.length(); i++) {
		System.out.print("*");
		}
		System.out.println();
		}

	private void run() {

		while (true) {
			//Print out heading and promp the user to select a park from the lisst of parks provided.
			printHeading("View Parks Interface");
			System.out.println("Select a Park for Further Details");
			List<Park> parks = parkDAO.getAllFromPark();
			Park quit = new Park();
			quit.setName("Quit");
			parks.add(quit);
			//Store the users choice in a Park object, checking to see if the user chose to end the program by selecting 0.
			Park parkChoice = null;
			try {
				parkChoice = (Park) parkMenu.getChoiceFromOptions(parks.toArray());
			} catch (CancelException e) {
				System.out.print("\nGoodbye!");
				System.exit(0);
			}
			if (parkChoice == quit) {
				System.out.print("\nGoodbye!");
				System.exit(0);
			}
			//If the user chose a park, they are then directed to the next method.
			handleViewCampgrounds(parkChoice);
		}
	}

	private void handleViewCampgrounds(Park parkChoice) {
		//Print out heading and description of park chosen.
		printHeading("Park Information Screen");
		System.out.println(parkChoice.getParkDetails());
		System.out.println(TextFormat.wrap(parkChoice.getDescription()));
		//Ask user to select from the next set of options and check again to see if they select 0.
		printHeading("Select a Command");
		String parkInfoChoice = "";
		try {
			parkInfoChoice = (String) parkMenu.getChoiceFromOptions(PARK_INFO_MENU_OPTIONS);
		} catch (CancelException e) {
			System.out.print("\nGoodbye!");
			System.exit(0);
		}
		//If the user selects an option from the list they are directed to the next method for the appropriate option.
		if (parkInfoChoice.equals(PARK_INFO_MENU_OPTION_VIEW_CAMPGROUNDS)) {
			handleCampgrounds(parkChoice);
		} else if (parkInfoChoice.equals(MENU_OPTION_SEARCH_FOR_AVAILABLE_RESERVATION)) {
			handleSearchReservation(parkChoice);
		}
	}

	private void handleCampgrounds(Park parkChoice) {
		//Print heading and display to the user a list of campgrounds within the selected park.
		printHeading("Park Campgrounds");
		System.out.println(campgroundList(parkChoice));
		String campgroundChoice = "";
		//Check the input to see if 0 was selected.
		try {
			campgroundChoice = (String) parkMenu.getChoiceFromOptions(CAMPGROUND_MENU_OPTIONS);
		} catch (CancelException e) {
			System.out.print("\nGoodbye!");
			System.exit(0);
		//check user input and direct them to the next method to search for available reservations. If they chose to return to the previous screen,
		//they are returned to the previous method/menu.
		}
		if (campgroundChoice.equals(MENU_OPTION_SEARCH_FOR_AVAILABLE_RESERVATION)) {
			handleSearchReservation(parkChoice);
		} else if (campgroundChoice.equals(MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN)) {
			handleViewCampgrounds(parkChoice);
		}
	}

	private void handleSearchReservation(Park parkChoice) {
		//Print heading and prompt them to enter the campground they wish to search in.
		printHeading("Search for Campground Reservation");
		String message = campgroundList(parkChoice);
		message += "\n\nWhich campground (enter 0 to cancel)? ";

		Campground campgroundChoice;
		//Again, checking the user input for 0.
		try {
			campgroundChoice = (Campground) parkMenu.getChoiceFromSearchOptions(message,campgroundDAO.getAllFromCampground(parkChoice).toArray());
		} catch (CancelException e) {
			return;
		}
		
		LocalDate fromDate = null;
		LocalDate toDate = null;
		// Asks for to date until a valid one is entered
		while (fromDate == null || fromDate.isBefore(LocalDate.now())) {
			fromDate = parkMenu.getValidDateFromUser(CAMPGROUND_RESERVATION_MENU_ARRIVAL_DATE);

			if (fromDate.isBefore(LocalDate.now())) {
				System.out.println("Date cannot be before today");
			}
		}

		// Asks for from date until a valid one is entered
		while (toDate == null || toDate.isBefore(fromDate)) {
			toDate = (LocalDate) parkMenu.getValidDateFromUser(CAMPGROUND_RESERVATION_MENU_DEPARTURE_DATE);
			
			if (toDate.isBefore(fromDate)) {
				System.out.println("Date cannot be before arrival date.");
			}
		}
		//Check the user input to see if the years for arrival date and departure date are the same.
		if ((fromDate.getYear() > 1 || toDate.getYear() < 12) && (campgroundChoice.getOpenFromMonth() > 1 || campgroundChoice.getOpenToMonth() < 12)) {
			System.out.println("Campground is closed during part this part of the year.");
			System.out.println("Please choose different dates");
			handleSearchReservation(parkChoice);
		//Check the user input to see if the users inputs are within a time the park is not open. If so they are asked to enter new dates.
		} else if (toDate.getMonthValue() < campgroundChoice.getOpenFromMonth() || fromDate.getMonthValue() > campgroundChoice.getOpenToMonth()) {
			System.out.println("Campground is closed during part this part of the year.");
			System.out.println("Please choose different dates");
			handleSearchReservation(parkChoice);
		} else {
			//Print heading and return available sites for the dates searched and site details..
			printHeading("Results Matching Your Search Criteria");
			List<Site> availableSites = siteDAO.getAvailableSites(campgroundChoice, toDate, fromDate);
			String siteDetails = "";
			siteDetails += String.format("%-12s %-12s %-12s %-15s %-12s %-12s", "Site No.", "Max Occup.", "Accessible?",
					"Max RV Length", "Utility", "Cost") + "\n";
			long duration = Duration.between(fromDate.atStartOfDay(), toDate.atStartOfDay()).toDays() + 1;
			BigDecimal cost = campgroundChoice.getDailyFee().multiply(new BigDecimal(duration));
			for (Site site : availableSites) {
				siteDetails += site.getSiteDetails(cost);
			}
			//User is directed to the next method to reserve a site.
			System.out.println(siteDetails);
			handleMakeReservation(availableSites, fromDate, toDate);
		}
	}
	
	private void handleMakeReservation(List<Site> availableSites, LocalDate fromDate, LocalDate toDate) {
		
		
		ArrayList<Long> siteOptions = new ArrayList<>();
		for (Site site : availableSites) {

			siteOptions.add(site.getSiteNumber());
		}
		//Prompt user to enter a site to be reserved.
		String siteMessage = "Which site should be reserved (enter 0 to cancel)? ";

		long reservationChoice;
		//Check if user entered 0 to cancel.
		try {
			reservationChoice = parkMenu.getValidSiteFromUserInput(siteMessage, siteOptions);
		} catch (CancelException e) {
			return;
		}
		//Ask the user to provide a name to reserve the site under.
		String reservationName = parkMenu.getReservationName(MAKE_RESERVATION_MENU_RESERVATION_NAME);

		Reservation reservation = new Reservation();
		reservation.setName(reservationName);
		reservation.setCreateDate(LocalDate.now());
		reservation.setFromDate(fromDate);
		reservation.setToDate(toDate);
		reservation.setSiteId(availableSites.get(siteOptions.indexOf(reservationChoice)).getSiteId());
		//Insert the reservation into the database and display to the user a confirmation Id.
		long reservationId = reservationDAO.makeReservation(reservation);

		System.out.print("Congratulations! Your reservation has been made! Your confirmation id is " + reservationId + 
				"\n" + "Happy camping! \n\n");
	}
	
	public String campgroundList(Park parkChoice) {
		//This method creates a list of campgrounds and their details formatted correctly to display to the user.
		List<Campground> campgroundsInPark = campgroundDAO.getAllFromCampground(parkChoice);
		System.out.println(parkChoice.getName() + " Campgrounds");
		String campgroundInfo = "";
		campgroundInfo = String.format("     " + "%-32s %-15s %-15s %-15s", "Name", "Open", "Close", "Daily Fee\n");
		System.out.println(campgroundInfo);
		String campgroundDetails = "";
		for (int i = 0; i < campgroundsInPark.size(); i++) {
			campgroundDetails += String.format("#" + (i + 1)) + "   " + campgroundsInPark.get(i).getCampgroundDetails() + "\n";
		}
		return campgroundDetails;
	}
}
	
