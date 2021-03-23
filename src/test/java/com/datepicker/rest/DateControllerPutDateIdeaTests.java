package com.datepicker.rest;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.datepicker.rest.models.DateDTO;

@SpringBootTest
@ActiveProfiles("test")

public class DateControllerPutDateIdeaTests {
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
	public void testPutDateIdeaValid() {		
		MockHttpServletResponse response = new MockHttpServletResponse();
		DateDTO testDate = new DateDTO();
		testDate.setCost(10.0);
		testDate.setDuration(3);
		testDate.setDateDescription("Test it");
		testDate.setDateName("testPutDateIdeaValid");
		
		List<DateIdea> dates = dateRepo.findAll();
		//No data yet
		Assertions.assertEquals(0,dates.size());
		
		controller.putDateIdea(testDate, response);
		
		//Make sure we got created back and the appropriate location header
		Assertions.assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());
		Assertions.assertTrue(response.getHeader("Location").startsWith("/viewDateIdea/"));
		
		dates = dateRepo.findAll();
		//1 Value entered
		Assertions.assertEquals(1,dates.size());
	}
	
	@Test
	public void testPutDateIdeaInvalid() {		
		MockHttpServletResponse response = new MockHttpServletResponse();
		DateDTO testDate = new DateDTO();
		testDate.setCost(10.0);
		testDate.setDuration(3);
		testDate.setDateDescription("Test it");
		
		//null value to make it invalid - validator tested in RestUtilsTest
		testDate.setDateName(null);
		
		List<DateIdea> dates = dateRepo.findAll();
		//No data yet
		Assertions.assertEquals(0,dates.size());
		
		try {
			controller.putDateIdea(testDate, response);
			Assertions.fail("Method did not throw expected exception");
		} catch (ResponseStatusException e) {
			Assertions.assertEquals(422, e.getRawStatusCode());
		}
		
		dates = dateRepo.findAll();
		//Still nothing
		Assertions.assertEquals(0,dates.size());
	}
}
