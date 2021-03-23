package com.datepicker.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.datepicker.DateApplication;
import com.datepicker.jpa.DateRespository;
import com.datepicker.jpa.DateSearchSpecification;
import com.datepicker.jpa.model.DateIdea;
import com.datepicker.rest.models.DateDTO;
import com.datepicker.rest.utils.RestUtils;

@RestController
public class DateController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DateApplication.class);
	
	//Mapper used to map between the DTO and JPA entities. 
	//Mostly just to avoid exposing the JPA directly via rest - objects are nearly identical
	private static final ModelMapper MAPPER = new ModelMapper();
	
	@Autowired
	private DateRespository dateRepo;

	
	/**
	 * Adds or updates a given idea
	 * 
	 * @param date
	 * @param response
	 */
	@PutMapping("/putDateIdea")
	public void putDateIdea(@RequestBody DateDTO date, HttpServletResponse response) {
		if (RestUtils.validateNewObject(date)) {
			LOGGER.info("Adding a new date idea");
			//If the input is valid, save it, overwriting existing data if the ID was provided
			DateIdea savedDate = dateRepo.save(MAPPER.map(date, DateIdea.class));
			response.addHeader("Location", "/viewDateIdea/" + savedDate.getDateId());
			response.setStatus(HttpServletResponse.SC_CREATED); 
		} else {
			LOGGER.info("Invalid new date idea received");
			//TODO: Updates to return what fields are invalid
			//Since invalid json will fail ahead of time, this is just an invalid entity
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid field contents");
		}
	}

	/**
	 * Finds a specific idea by ID
	 * 
	 * @param dateId
	 * @return the date idea object, or 404 if none is found
	 */
	@GetMapping("/viewDateIdea/{dateId}")
	public @ResponseBody DateDTO viewDateIdea(@PathVariable("dateId") Long dateId) {
		LOGGER.info("Fetching Date Idea by id: " + dateId);
		DateIdea response = dateRepo.findById(dateId).orElse(null);
		
		if (response == null) {
			//Return a 404 if there wasn't anything returned
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No date idea with that ID");
		}
		
		return MAPPER.map(response, DateDTO.class);
	}
	
	/**
	 * Deletes a specific idea by ID
	 * 
	 * @param dateId
	 */
	@DeleteMapping("/deleteDateIdea/{dateId}")
	public void deleteDateIdea(@PathVariable("dateId") Long dateId, HttpServletResponse response) {
		LOGGER.info("Deleting Date Idea by id: " + dateId);
		try {
			dateRepo.deleteById(dateId);
		} catch (EmptyResultDataAccessException e) {
			//No data to delete - 404 not found
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No date idea with that ID");
		}
		response.setStatus(HttpServletResponse.SC_NO_CONTENT); 
	}
	
	/**
	 * Searches for any ideas matching the criteria of the given input object
	 * 
	 * @param criteria
	 * @return a list of all matching ideas, or an empty list if there are none
	 */
	@PostMapping("/findDateIdeas")
	public @ResponseBody List<DateDTO> findDateIdeas(@RequestBody DateDTO criteria) {
		//TODO: Updates to return information on invalid fields if criteria CANNOT return results
		//Find the list and map them to the DTO
		LOGGER.info("Grabbing Date Ideas");
		return dateRepo.findAll(new DateSearchSpecification(MAPPER.map(criteria, DateIdea.class))).
				stream().map((dateIdea) -> MAPPER.map(dateIdea, DateDTO.class)).collect(Collectors.toList());
	}
	
	/**
	 * Gets one random idea based on input criteria
	 * 
	 * @param criteria
	 * @return The idea selected at random, or one with a message telling the user that none was found<br>
	 * and encouraging them to add one
	 */
	@PostMapping("/randomDateIdea")
	public @ResponseBody DateDTO randomDateIdea(@RequestBody DateDTO criteria) {
		//TODO: Updates to return information on invalid fields if criteria CANNOT return results
		//Find objects based on the input DTO
		LOGGER.info("Picking a random date");
		List<DateIdea> dateIdeas = dateRepo.findAll(new DateSearchSpecification(MAPPER.map(criteria, DateIdea.class)));		
		
		return MAPPER.map(RestUtils.getRandomIdea(dateIdeas), DateDTO.class);
	}
}
	