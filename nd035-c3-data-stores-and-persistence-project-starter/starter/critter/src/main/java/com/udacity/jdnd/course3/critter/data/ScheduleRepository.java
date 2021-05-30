package com.udacity.jdnd.course3.critter.data;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//Note: Spring Data JPA instead of Hibernate for repository

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    //Use JPQL for Schedule queries below

    @Query("select s from Schedule s where :employeeID member of s.employees")
    List<Schedule> findAllSchedulesForEmployee(@Param("employeeID") Employee employeeID);

    @Query("select s from Schedule s where :petID member of s.pets")
    List<Schedule> findAllSchedulesForPet(@Param("petID") Pet petID);

    @Query("select s from Schedule s where :custID member of s.customers")
    List<Schedule> findAllSchedulesForCustomer(@Param("custID") Customer custID);

}
