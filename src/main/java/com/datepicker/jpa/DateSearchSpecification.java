package com.datepicker.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;

import com.datepicker.jpa.model.DateIdea;

/**
 * This class is used to create a specification to search from a partially populated DateIdea
 * 
 * @see com.datepicker.model.DateIdea
 * 
 * @author Nathan
 *
 */
public class DateSearchSpecification implements Specification<DateIdea> {
	private static final long serialVersionUID = 6227542028881220219L;
	private DateIdea criteria;
	
	public DateSearchSpecification(DateIdea criteria) {
		this.criteria = criteria;
	}
	
	@Override
	public Predicate toPredicate(Root<DateIdea> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		//Excludes cost/duration for the less than check below
		//Excludes description as I haven't implemented any method to search that by keywords
		//Gets any date names containing what was provided with ignore case
		ExampleMatcher matcher = ExampleMatcher.matchingAll()
				.withIgnoreCase()
				.withIgnorePaths("cost")
				.withIgnorePaths("duration")
				.withIgnorePaths("dateDescription")
				.withMatcher("dateName", GenericPropertyMatchers.contains());
		
		//Check that the cost is lower than or equal to the provided value
		if (criteria.getCost() != null) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("cost"), criteria.getCost()));
		}
		
		//Check that the duration is lower than or equal to the provided value
		if (criteria.getDuration() != null) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("duration"), criteria.getDuration()));
		}
		
		//Add the example on which automatically checks the rest of the fields
		predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, criteriaBuilder, Example.of(criteria, matcher)));
		
		return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	}
}
