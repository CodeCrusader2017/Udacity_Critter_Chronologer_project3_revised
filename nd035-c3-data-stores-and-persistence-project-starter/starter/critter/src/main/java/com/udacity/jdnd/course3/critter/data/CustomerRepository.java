package com.udacity.jdnd.course3.critter.data;

import com.udacity.jdnd.course3.critter.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Note: Spring Data JPA instead of Hibernate for repository

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
