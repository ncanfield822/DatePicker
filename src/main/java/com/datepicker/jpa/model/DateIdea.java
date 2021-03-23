package com.datepicker.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Hosues the date information
 * 
 * @author Nathan
 *
 */
@Entity
@Table(name="DATE_IDEAS")
public class DateIdea {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="DATE_ID")
	private Long dateId;

	@Column(name="DATE_NAME")
	private String dateName;
	
	@Column(name="DATE_DESCRIPTION")
	private String dateDescription;
	
	@Column(name="DURATION")
	private Integer duration;
	
	@Column(name="COST")
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
