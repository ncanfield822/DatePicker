package com.datepicker.rest;

import java.util.List;

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

public class DateControllerFindDateIdeasTests {
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
		
		List<DateDTO> dates = controller.findDateIdeas(new DateDTO());
		//No data yet
		Assertions.assertEquals(0,dates.size());
		
		//Add one
		dateRepo.save(testDate);
				
		dates = controller.findDateIdeas(new DateDTO());
		//Should pull back 1
		Assertions.assertEquals(1,dates.size());
		
		//Add another
		testDate = new DateIdea();
		testDate.setCost(20.0);
		testDate.setDuration(4);
		testDate.setDateDescription("Test it again");
		testDate.setDateName("testFindIdeasNoFilter2");
		dateRepo.save(testDate);

		dates = controller.findDateIdeas(new DateDTO());
		//Should pull back 2
		Assertions.assertEquals(2,dates.size());
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

		List<DateDTO> dates = controller.findDateIdeas(new DateDTO());
		//Should pull back 2
		Assertions.assertEquals(2,dates.size());
		
		DateDTO filterObj = new DateDTO();
		
		filterObj.setDateName("test");
		dates = controller.findDateIdeas(filterObj);
		//Should still pull back 2 - name is contains both contain test
		Assertions.assertEquals(2,dates.size());

		filterObj.setDateName("TEST");
		dates = controller.findDateIdeas(filterObj);
		//Should still pull back 2 - name is contains both contain test - should ignore case
		Assertions.assertEquals(2,dates.size());
		
		filterObj.setDateName("2");
		dates = controller.findDateIdeas(filterObj);
		//Should still pull back 1
		Assertions.assertEquals(1,dates.size());
		
		filterObj.setDateName("test2");
		dates = controller.findDateIdeas(filterObj);
		//Should still pull back 1
		Assertions.assertEquals(1,dates.size());
		
		filterObj.setDateName("nothing matches this");
		dates = controller.findDateIdeas(filterObj);
		//Should not return any results
		Assertions.assertEquals(0,dates.size());
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

		List<DateDTO> dates = controller.findDateIdeas(new DateDTO());
		//Should pull back 2
		Assertions.assertEquals(2,dates.size());
		
		DateDTO filterObj = new DateDTO();
		
		filterObj.setDateDescription("this would filter out everything if it searches on it");
		dates = controller.findDateIdeas(filterObj);
		//Should still pull back 2 - description doesn't filter
		Assertions.assertEquals(2,dates.size());
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

		List<DateDTO> dates = controller.findDateIdeas(new DateDTO());
		//Should pull back 2
		Assertions.assertEquals(2,dates.size());
		
		DateDTO filterObj = new DateDTO();
		
		filterObj.setCost(20.0);
		dates = controller.findDateIdeas(filterObj);
		//Should still pull back 2 - both are 20 or less
		Assertions.assertEquals(2,dates.size());
		
		filterObj.setCost(15.0);
		dates = controller.findDateIdeas(filterObj);
		//Should pull back 1 - only the first is under 15
		Assertions.assertEquals(1,dates.size());
		
		filterObj.setCost(5.0);
		dates = controller.findDateIdeas(filterObj);
		//Should not pull anything back, nothing is under 5
		Assertions.assertEquals(0,dates.size());
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

		List<DateDTO> dates = controller.findDateIdeas(new DateDTO());
		//Should pull back 2
		Assertions.assertEquals(2,dates.size());
		
		DateDTO filterObj = new DateDTO();
		
		filterObj.setDuration(6);
		dates = controller.findDateIdeas(filterObj);
		//Should still pull back 2 - both are 6 or less
		Assertions.assertEquals(2,dates.size());
		
		filterObj.setDuration(5);
		dates = controller.findDateIdeas(filterObj);
		//Should pull back 1 - only the first is under 5
		Assertions.assertEquals(1,dates.size());
		
		filterObj.setDuration(2);
		dates = controller.findDateIdeas(filterObj);
		//Should not pull anything back, nothing is under 2
		Assertions.assertEquals(0,dates.size());
	}
}
