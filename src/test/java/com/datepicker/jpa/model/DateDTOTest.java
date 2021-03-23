package com.datepicker.jpa.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DateDTOTest {
	@Test
	public void testDateId() {
		DateIdea testObj = new DateIdea();
		Long testVal = 1l;
		testObj.setDateId(testVal);
		Assertions.assertEquals(testVal, testObj.getDateId());
	}
	@Test
	public void testDateName() {
		DateIdea testObj = new DateIdea();
		String testVal = "Testname";
		testObj.setDateName(testVal);
		Assertions.assertEquals(testVal, testObj.getDateName());
	}
	@Test
	public void testDateDescription() {
		DateIdea testObj = new DateIdea();
		String testVal = "Test Description";
		testObj.setDateDescription(testVal);
		Assertions.assertEquals(testVal, testObj.getDateDescription());
	}
	@Test
	public void testCost() {
		DateIdea testObj = new DateIdea();
		Double testVal = 11.11;
		testObj.setCost(testVal);
		Assertions.assertEquals(testVal, testObj.getCost());
	}
	@Test
	public void testDuration() {
		DateIdea testObj = new DateIdea();
		Integer testVal = 123;
		testObj.setDuration(testVal);
		Assertions.assertEquals(testVal, testObj.getDuration());
	}
}
