package com.datepicker.rest.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DateDTOTest {
	@Test
	public void testDateId() {
		DateDTO testObj = new DateDTO();
		Long testVal = 1l;
		testObj.setDateId(testVal);
		Assertions.assertEquals(testVal, testObj.getDateId());
	}
	@Test
	public void testDateName() {
		DateDTO testObj = new DateDTO();
		String testVal = "Testname";
		testObj.setDateName(testVal);
		Assertions.assertEquals(testVal, testObj.getDateName());
	}
	@Test
	public void testDateDescription() {
		DateDTO testObj = new DateDTO();
		String testVal = "Test Description";
		testObj.setDateDescription(testVal);
		Assertions.assertEquals(testVal, testObj.getDateDescription());
	}
	@Test
	public void testCost() {
		DateDTO testObj = new DateDTO();
		Double testVal = 11.11;
		testObj.setCost(testVal);
		Assertions.assertEquals(testVal, testObj.getCost());
	}
	@Test
	public void testDuration() {
		DateDTO testObj = new DateDTO();
		Integer testVal = 123;
		testObj.setDuration(testVal);
		Assertions.assertEquals(testVal, testObj.getDuration());
	}
}
