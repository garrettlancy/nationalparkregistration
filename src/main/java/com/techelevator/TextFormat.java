package com.techelevator;

public final class TextFormat {
	
	private final static int MAX_WIDTH = 70;

	public final static String wrap(String longString) {
		
		//Takes the string passed in and stores the string lines of text into an array with the lines of text in each index limited by a set value.
		//Used on the park descriptions to break down the line of text into a paragraph that can be read without scrolling in the command line application.
	    String[] splittedString = longString.split(" ");
	    String resultString = "";
	    String lineString = "";

	    for (int i = 0; i < splittedString.length; i++) {
	        if (lineString.isEmpty()) {
	            lineString += splittedString[i] + " ";
	        } else if (lineString.length() + splittedString[i].length() < MAX_WIDTH) {
	            lineString += splittedString[i] + " ";
	        } else {
	            resultString += lineString + "\n";
	            lineString = "";
	        }
	    }

	    if(!lineString.isEmpty()){
	            resultString += lineString + "\n";
	    }

	    return resultString;
	}
}
