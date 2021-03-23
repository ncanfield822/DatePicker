package com.datepicker.rest.utils;


import java.util.List;
import java.util.Random;

import com.datepicker.jpa.model.DateIdea;
import com.datepicker.rest.models.DateDTO;

/**
 * Utility class for the rest controller.
 * 
 * @author Nathan
 *
 */
public class RestUtils {
	
	//Private constructor for util class
	private RestUtils() {}
	
	/**
	 * Validates an input date used for updates or insertion of a new date object
	 * 
	 * @param date
	 * @return true if the object is valid
	 */
	public static Boolean validateNewObject(DateDTO date) {
		Boolean valid = true;
		
		if (date.getDateName() == null || 
				"".equals(date.getDateName()) || 
				date.getDateName().length() > 45) {
			//DateName is required and cannot be more than 45 characters
			valid = false;
		} else if (date.getDateDescription() == null ||
				"".equals(date.getDateDescription()) ||
				date.getDateDescription().length() > 255) {
			//DateDescription is required and maxes out at 255 characters
			valid = false;
		} else if (date.getCost() == null ||
				date.getCost() < 0.0) {
			//Cost must be populated and at least 0
			valid = false;
		} else if (date.getDuration() == null || 
				date.getDuration() < 1) {
			//Duration must be populated and at least 1			
			valid = false;
		}
		
		return valid;
	}
	
	/**
	 * Returns one dateIdea from a list or a default one if none are in the list
	 * 
	 * @param dateIdeas
	 * @return The idea selected at random, or the default one
	 */
	public static DateIdea getRandomIdea(List<DateIdea> dateIdeas) {
		DateIdea selection;	
		
		if (!dateIdeas.isEmpty()) {
			//If there's at least one object, pick a random one and return it
			Random random = new Random();
			int randDate = random.nextInt(dateIdeas.size());
			selection = dateIdeas.get(randDate);
		} else {
			//If there's no objects, return a default one with a short message
			selection = new DateIdea();
			selection.setDateName("Your choice!");
			selection.setDateDescription("We didn't find any dates matching those criteria, so you'll have to come up with some.\n"
					+ "\n"
					+ "Be sure to add some when you come up with them!");
		}
		
		return selection;
	}
}
