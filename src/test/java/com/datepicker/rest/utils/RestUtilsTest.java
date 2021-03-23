package com.datepicker.rest.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.datepicker.jpa.model.DateIdea;
import com.datepicker.rest.models.DateDTO;

public class RestUtilsTest {
	@Test
	public void testValidateNewObjectValid() {
		DateDTO testObj = new DateDTO();
		testObj.setCost(10.0);
		testObj.setDateDescription("Test description");
		testObj.setDateName("Test");
		testObj.setDuration(20);
		
		Assertions.assertTrue(RestUtils.validateNewObject(testObj));
	}
	
	@Test
	public void testValidateNewObjectInvalidCost() {
		DateDTO testObj = new DateDTO();
		testObj.setCost(-10.0);
		testObj.setDateDescription("Test description");
		testObj.setDateName("Test");
		testObj.setDuration(20);
		
		Assertions.assertFalse(RestUtils.validateNewObject(testObj));
	}
	
	@Test
	public void testValidateNewObjectNullCost() {
		DateDTO testObj = new DateDTO();
		testObj.setCost(null);
		testObj.setDateDescription("Test description");
		testObj.setDateName("Test");
		testObj.setDuration(20);
		
		Assertions.assertFalse(RestUtils.validateNewObject(testObj));
	}
	
	@Test
	public void testValidateNewObjectBlankDescription() {
		DateDTO testObj = new DateDTO();
		testObj.setCost(10.0);
		testObj.setDateDescription("");
		testObj.setDateName("Test");
		testObj.setDuration(20);
		
		Assertions.assertFalse(RestUtils.validateNewObject(testObj));
	}
	
	@Test
	public void testValidateNewObjectNullDescription() {
		DateDTO testObj = new DateDTO();
		testObj.setCost(10.0);
		testObj.setDateDescription(null);
		testObj.setDateName("Test");
		testObj.setDuration(20);
		
		Assertions.assertFalse(RestUtils.validateNewObject(testObj));
	}

	@Test
	public void testValidateNewObjectLongDescription() {
		DateDTO testObj = new DateDTO();
		testObj.setCost(10.0);
		testObj.setDateDescription("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
				+ "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
				+ "01234567890123456789012345678901234567890123456789012345");
		testObj.setDateName("Test");
		testObj.setDuration(20);
		
		Assertions.assertFalse(RestUtils.validateNewObject(testObj));
	}
	
	@Test
	public void testValidateNewObjectBlankName() {
		DateDTO testObj = new DateDTO();
		testObj.setCost(10.0);
		testObj.setDateDescription("Test description");
		testObj.setDateName("");
		testObj.setDuration(20);
		
		Assertions.assertFalse(RestUtils.validateNewObject(testObj));
	}

	@Test
	public void testValidateNewObjectNullName() {
		DateDTO testObj = new DateDTO();
		testObj.setCost(10.0);
		testObj.setDateDescription("Test description");
		testObj.setDateName(null);
		testObj.setDuration(20);
		
		Assertions.assertFalse(RestUtils.validateNewObject(testObj));
	}
	
	@Test
	public void testValidateNewObjectLongName() {
		DateDTO testObj = new DateDTO();
		testObj.setCost(10.0);
		testObj.setDateDescription("Test description");
		testObj.setDateName("0123456789012345678901234567890123456789012345");
		testObj.setDuration(20);
		
		Assertions.assertFalse(RestUtils.validateNewObject(testObj));
	}
	
	@Test
	public void testValidateNewObjectInvalidDuration() {
		DateDTO testObj = new DateDTO();
		testObj.setCost(10.0);
		testObj.setDateDescription("Test description");
		testObj.setDateName("Test");
		testObj.setDuration(0);
		
		Assertions.assertFalse(RestUtils.validateNewObject(testObj));
	}
	
	@Test
	public void testValidateNewObjectNullDuration() {
		DateDTO testObj = new DateDTO();
		testObj.setCost(10.0);
		testObj.setDateDescription("Test description");
		testObj.setDateName("Test");
		testObj.setDuration(null);
		
		Assertions.assertFalse(RestUtils.validateNewObject(testObj));
	}
	
	@Test
	public void testGetRandomDTOEmptyList() {
		DateIdea testObj = RestUtils.getRandomIdea(new ArrayList<DateIdea>());
		Assertions.assertEquals("Your choice!", testObj.getDateName());
		Assertions.assertEquals("We didn't find any dates matching those criteria, so you'll have to come up with some.\n"
				+ "\n"
				+ "Be sure to add some when you come up with them!", testObj.getDateDescription());
	}
	
	@Test
	public void testGetRandomDTOOneObject() {
		List<DateIdea> testList = new ArrayList<DateIdea>();
		
		DateIdea testObj = new DateIdea();
		testObj.setDateName("Test Object");
		testList.add(testObj);
		
		Assertions.assertEquals(testObj, RestUtils.getRandomIdea(testList));
	}
	
	@Test
	public void testGetRandomDTOMultipleObjects() {
		List<DateIdea> testList = new ArrayList<DateIdea>();

		DateIdea testObj = new DateIdea();
		testObj.setDateName("Test Object");
		testList.add(testObj);
		
		testObj = new DateIdea();
		testObj.setDateName("Different Object");
		testList.add(testObj);
		
		testObj = RestUtils.getRandomIdea(testList);
		
		Boolean diffObject = false;
		
		//Try to get a different one of the two objects 100 times - it should get a different one well before that point if working
		for (int i = 0; i < 100; i++) {
			DateIdea nextObject = RestUtils.getRandomIdea(testList);
			if (!nextObject.equals(testObj)) {
				diffObject = true;
				break;
			}
		}
		
		Assertions.assertTrue(diffObject);
	}
}
