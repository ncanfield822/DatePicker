package com.datepicker.rest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.datepicker.jpa.DateRespository;
import com.datepicker.jpa.model.DateIdea;
import com.datepicker.rest.models.DateDTO;

@SpringBootTest
@ActiveProfiles("test")

public class DateControllerRandomDateIdeaTests {
	@Autowired
	private DateRespository dateRepo;
	
	@Autowired
	private DateController controller;
	
	@AfterEach
	public void clearTestDB() {
		//Clears out the test DB after each test
		dateRepo.deleteAll();
	}

	@Test
	public void testFindIdeasNoFilter() {
		DateIdea testDate = new DateIdea();
		testDate.setCost(10.0);
		testDate.setDuration(3);
		testDate.setDateDescription("Test it");
		testDate.setDateName("testFindIdeasNoFilter");
		
		DateDTO date = controller.randomDateIdea(new DateDTO());
		//No data yet
		Assertions.assertEquals("Your choice!",date.getDateName());
		
		//Add one
		dateRepo.save(testDate);
		
		DateDTO filterObj = new DateDTO();
				
		date = controller.randomDateIdea(filterObj);
		//Should pull back 1
		Assertions.assertFalse(retryForDifferentObject(date,filterObj));
		
		//Add another
		testDate = new DateIdea();
		testDate.setCost(20.0);
		testDate.setDuration(4);
		testDate.setDateDescription("Test it again");
		testDate.setDateName("testFindIdeasNoFilter2");
		dateRepo.save(testDate);

		date = controller.randomDateIdea(filterObj);
		//Should pull back 2
		Assertions.assertTrue(retryForDifferentObject(date,filterObj));
	}
	
	@Test
	public void testFindIdeasWithNameFilter() {
		//Add a date idea
		DateIdea testDate = new DateIdea();
		testDate.setCost(10.0);
		testDate.setDuration(3);
		testDate.setDateDescription("Test it");
		testDate.setDateName("testFindIdeasNoFilter");
		dateRepo.save(testDate);
		
		//Add another
		testDate = new DateIdea();
		testDate.setCost(20.0);
		testDate.setDuration(4);
		testDate.setDateDescription("Test it again");
		testDate.setDateName("test2");
		dateRepo.save(testDate);

		DateDTO filterObj = new DateDTO();
		
		DateDTO date = controller.randomDateIdea(filterObj);
		//Should pull back 2
		Assertions.assertTrue(retryForDifferentObject(date,filterObj));
				
		filterObj.setDateName("test");
		date = controller.randomDateIdea(filterObj);
		//Should still pull back 2 - name is contains both contain test
		Assertions.assertTrue(retryForDifferentObject(date,filterObj));

		filterObj.setDateName("TEST");
		date = controller.randomDateIdea(filterObj);
		//Should still pull back 2 - name is contains both contain test - should ignore case
		Assertions.assertTrue(retryForDifferentObject(date,filterObj));
		
		filterObj.setDateName("2");
		date = controller.randomDateIdea(filterObj);
		//Should still pull back 1
		Assertions.assertFalse(retryForDifferentObject(date,filterObj));
		
		filterObj.setDateName("test2");
		date = controller.randomDateIdea(filterObj);
		//Should still pull back 1
		Assertions.assertFalse(retryForDifferentObject(date,filterObj));
		
		filterObj.setDateName("nothing matches this");
		date = controller.randomDateIdea(filterObj);
		//Should not return any results
		Assertions.assertEquals("Your choice!",date.getDateName());
	}
	
	@Test
	public void testFindIdeasWithIgnoredDescriptonFilter() {
		//Add a date idea
		DateIdea testDate = new DateIdea();
		testDate.setCost(10.0);
		testDate.setDuration(3);
		testDate.setDateDescription("Test it");
		testDate.setDateName("testFindIdeasNoFilter");
		dateRepo.save(testDate);
		
		//Add another
		testDate = new DateIdea();
		testDate.setCost(20.0);
		testDate.setDuration(4);
		testDate.setDateDescription("Test it again");
		testDate.setDateName("test2");
		dateRepo.save(testDate);	
		
		DateDTO filterObj = new DateDTO();

		DateDTO date = controller.randomDateIdea(filterObj);
		//Should pull back 2
		Assertions.assertTrue(retryForDifferentObject(date,filterObj));
		
		filterObj.setDateDescription("this would filter out everything if it searches on it");
		date = controller.randomDateIdea(filterObj);
		//Should still pull back 2 - description doesn't filter
		Assertions.assertTrue(retryForDifferentObject(date,filterObj));
	}
	
	@Test
	public void testFindIdeasWithCostFilter() {
		//Add a date idea
		DateIdea testDate = new DateIdea();
		testDate.setCost(10.0);
		testDate.setDuration(3);
		testDate.setDateDescription("Test it");
		testDate.setDateName("testFindIdeasNoFilter");
		dateRepo.save(testDate);
		
		//Add another
		testDate = new DateIdea();
		testDate.setCost(20.0);
		testDate.setDuration(6);
		testDate.setDateDescription("Test it again");
		testDate.setDateName("test2");
		dateRepo.save(testDate);
		
		DateDTO filterObj = new DateDTO();
		
		DateDTO date = controller.randomDateIdea(filterObj);
		//Should pull back 2
		Assertions.assertTrue(retryForDifferentObject(date,filterObj));		
		
		filterObj.setCost(20.0);
		date = controller.randomDateIdea(filterObj);
		//Should still pull back 2 - both are 20 or less
		Assertions.assertTrue(retryForDifferentObject(date,filterObj));
		
		filterObj.setCost(15.0);
		date = controller.randomDateIdea(filterObj);
		//Should pull back 1 - only the first is under 15
		Assertions.assertFalse(retryForDifferentObject(date,filterObj));
		
		filterObj.setCost(5.0);
		date = controller.randomDateIdea(filterObj);
		//Should not pull anything back, nothing is under 5
		Assertions.assertEquals("Your choice!",date.getDateName());
	}
	
	@Test
	public void testFindIdeasWithDurationFilter() {
		//Add a date idea
		DateIdea testDate = new DateIdea();
		testDate.setCost(10.0);
		testDate.setDuration(3);
		testDate.setDateDescription("Test it");
		testDate.setDateName("testFindIdeasNoFilter");
		dateRepo.save(testDate);
		
		//Add another
		testDate = new DateIdea();
		testDate.setCost(20.0);
		testDate.setDuration(6);
		testDate.setDateDescription("Test it again");
		testDate.setDateName("test2");
		dateRepo.save(testDate);
		
		DateDTO filterObj = new DateDTO();
		
		DateDTO date = controller.randomDateIdea(filterObj);
		//Should pull back 2
		Assertions.assertTrue(retryForDifferentObject(date,filterObj));
		
		filterObj.setDuration(6);
		date = controller.randomDateIdea(filterObj);
		//Should still pull back 2 - both are 6 or less
		Assertions.assertTrue(retryForDifferentObject(date,filterObj));
		
		filterObj.setDuration(5);
		date = controller.randomDateIdea(filterObj);
		//Should pull back 1 - only the first is under 5
		Assertions.assertFalse(retryForDifferentObject(date,filterObj));
		
		filterObj.setDuration(2);
		date = controller.randomDateIdea(filterObj);
		//Should not pull anything back, nothing is under 2
		//Default object should be returned
		Assertions.assertEquals("Your choice!",date.getDateName());
	}

	/**
	 * Tries to get a different object from the random date idea endpoint - validates based on date names - ensure they are different for test objects
	 * 
	 * @param testObj
	 * @param filterObj
	 * @return
	 */
	private Boolean retryForDifferentObject(DateDTO testObj, DateDTO filterObj) {
		Boolean diffObject = false;
		
		//Try to get a different one of the objects 100 times
		//It should get a different one well before that point if more than one exists
		for (int i = 0; i < 100; i++) {
			DateDTO nextObject = controller.randomDateIdea(filterObj);
			//Check the date name, its different for both objects in these tests
			if (!nextObject.getDateName().equals(testObj.getDateName())) {
				diffObject = true;
				break;
			}
		}
		
		return diffObject;
	}
}
