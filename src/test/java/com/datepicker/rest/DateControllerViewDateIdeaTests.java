package com.datepicker.rest;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import com.datepicker.jpa.DateRespository;
import com.datepicker.jpa.model.DateIdea;
import com.datepicker.rest.models.DateDTO;

@SpringBootTest
@ActiveProfiles("test")

public class DateControllerViewDateIdeaTests {
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
	public void testViewDateIdeaValid() {
		DateIdea testDate = new DateIdea();
		testDate.setCost(10.0);
		testDate.setDuration(3);
		testDate.setDateDescription("Test it");
		testDate.setDateName("testFindIdeasNoFilter");
		
		testDate = dateRepo.save(testDate);
		
		DateDTO returnedDate = controller.viewDateIdea(testDate.getDateId());
		
		//Make sure we got created back and the appropriate location header
		Assertions.assertEquals(returnedDate.getCost(), testDate.getCost());
		Assertions.assertEquals(returnedDate.getDuration(), testDate.getDuration());
		Assertions.assertEquals(returnedDate.getDateName(), testDate.getDateName());
		Assertions.assertEquals(returnedDate.getDateDescription(), testDate.getDateDescription());
	}
	
	@Test
	public void testViewDateIdeaInvalid() {			
		List<DateIdea> dates = dateRepo.findAll();
		//No data exists
		Assertions.assertEquals(0,dates.size());
		
		try {
			controller.viewDateIdea(70l);
			Assertions.fail("Method did not throw expected exception");
		} catch (ResponseStatusException e) {
			Assertions.assertEquals(404, e.getRawStatusCode());
		}
	}
}
