package com.datepicker.rest;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import com.datepicker.jpa.DateRespository;
import com.datepicker.jpa.model.DateIdea;

@SpringBootTest
@ActiveProfiles("test")
public class DateControllerDeleteDateIdeaTests {
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
		MockHttpServletResponse response = new MockHttpServletResponse();
		DateIdea testDate = new DateIdea();
		testDate.setCost(10.0);
		testDate.setDuration(3);
		testDate.setDateDescription("Test it");
		testDate.setDateName("testFindIdeasNoFilter");
		
		testDate = dateRepo.save(testDate);

		List<DateIdea> dates = dateRepo.findAll();
		//One object
		Assertions.assertEquals(1,dates.size());

		controller.deleteDateIdea(testDate.getDateId(), response);
		
		//Should return a 204
		Assertions.assertEquals(204, response.getStatus());
		
		dates = dateRepo.findAll();
		//Data deleted
		Assertions.assertEquals(0,dates.size());
	}
	
	@Test
	public void testViewDateIdeaInvalid() {			
		MockHttpServletResponse response = new MockHttpServletResponse();
		List<DateIdea> dates = dateRepo.findAll();
		//No data yet
		Assertions.assertEquals(0,dates.size());
		
		try {
			controller.deleteDateIdea(70l, response);
			Assertions.fail("Method did not throw expected exception");
		} catch (ResponseStatusException e) {
			Assertions.assertEquals(404, e.getRawStatusCode());
		}
	}
}
