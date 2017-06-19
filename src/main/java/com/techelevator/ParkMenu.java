package com.techelevator;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ParkMenu {

	private PrintWriter out;
	private Scanner in;

	public ParkMenu(InputStream input, OutputStream output) {
		
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}
	//Displays the menu options provided until the user selects one. Then returns the input as an object.
	public Object getChoiceFromOptions(Object[] options) throws CancelException {
		
		Object choice = null;
		while(choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}
	//Displays the menu option for searching reservations, until the user selects one. Then returns the input as an object.
	public Object getChoiceFromSearchOptions(String message, Object[] options) throws CancelException {
		Object choice = null;
		while(choice == null) {
			out.print(message);
			out.flush();
			choice = getChoiceFromUserInput(options);
			if(choice == null) {
				out.println("\nInvalid selection, please choose again.\n");
				out.flush();
			}
		}
		return choice;
	}
	//Check the users input to see if it is an option that was provided.
	private Object getChoiceFromUserInput(Object[] options) throws CancelException {
		
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if(selectedOption == 0 ) {
				throw new CancelException();
			}
			if(selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch(NumberFormatException e) {
			
		}
		if(choice == null) {
			out.println("\n*** "+userInput+" is not a valid option ***\n");
			out.flush();
		}
		return choice;
	}
	//Check date provided by user to see if it is in the correct format requested.
	public LocalDate getValidDateFromUser(String message) {
		
		LocalDate reservationDate = null;
		while (reservationDate == null) {
			out.print(message + " ");
			out.flush();
			String userInput = in.nextLine();
			SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yyyy");
			Date date;
		//If the input is not in the correct format they are notified and prompted again.
			try {
				date = parser.parse(userInput);
				reservationDate = new java.sql.Date(date.getTime()).toLocalDate();
			} catch (ParseException e) {
				out.println("\nPlease enter a valid date.");
				out.flush();
			}
		}
		return reservationDate;
	}
	//Check user input to see if it is one of the available sites for the dates searched.
	public long getValidSiteFromUserInput(String message, ArrayList<Long> availableSites) throws CancelException {
		
		Long choice = null;
		boolean validChoice = false;
		while (!validChoice) {
			out.print(message);
			out.flush();
			String userInput = in.nextLine();
			choice = Long.parseLong(userInput);
			if(choice == 0) {
				throw new CancelException();
			}
		//If the input is not valid, they are notified and prompted again.
			if (!(availableSites.contains(choice))) {
				out.println("\nInvalid selection, please choose again.\n");
				out.flush();
			} else {
				validChoice = true;
			}
		}
		return choice;
	}
	//Display the options provided and numbers them in order. User is asked to input the number associated with desired option.
	private void displayMenuOptions(Object[] options) {
		
		out.println();
		for(int i = 0; i < options.length; i++) {
			int optionNum = i+1;
			out.println(optionNum+") "+options[i]);
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}
	//Display the question, asking the user for a reservation name and returns the users input.
	public String getReservationName(String message) {
		
		out.print(message);
		out.flush();
		return in.nextLine();
	}
}
