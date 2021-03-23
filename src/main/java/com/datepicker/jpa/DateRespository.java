package com.datepicker.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.datepicker.jpa.model.DateIdea;

/**
 * Repo for the date picker app
 * 
 * @author Nathan
 *
 */
@Repository
public interface DateRespository extends JpaRepository<DateIdea,Long>, JpaSpecificationExecutor<DateIdea> {
}
