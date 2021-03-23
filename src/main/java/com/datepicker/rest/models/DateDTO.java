package com.datepicker.rest.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DateDTO {
	private Long dateId;
	private String dateName;
	private String dateDescription;
	private Integer duration;
	private Double cost;

	public Long getDateId() {
		return dateId;
	}

	public void setDateId(Long dateId) {
		this.dateId = dateId;
	}

	public String getDateName() {
		return dateName;
	}

	public void setDateName(String dateName) {
		this.dateName = dateName;
	}

	public String getDateDescription() {
		return dateDescription;
	}

	public void setDateDescription(String dateDescription) {
		this.dateDescription = dateDescription;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}
}
