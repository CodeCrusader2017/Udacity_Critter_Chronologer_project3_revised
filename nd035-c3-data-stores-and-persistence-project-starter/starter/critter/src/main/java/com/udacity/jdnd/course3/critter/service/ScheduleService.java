package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.CustomerRepository;
import com.udacity.jdnd.course3.critter.data.EmployeeRepository;
import com.udacity.jdnd.course3.critter.data.PetRepository;
import com.udacity.jdnd.course3.critter.data.ScheduleRepository;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public Schedule getSchedule(Long schedId) {
        return scheduleRepository.getOne(schedId);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    //This uses a query in the repository using JPQL
    public List<Schedule> getAllSchedulesForEmployee(Employee employee) {
        return scheduleRepository.findAllSchedulesForEmployee(employee);
    }

    //This uses a query in the repository using JPQL
    public List<Schedule> getAllSchedulesForPet(Pet pet) {
        return scheduleRepository.findAllSchedulesForPet(pet);
    }

    //This uses a query in the repository using JPQL
    public List<Schedule> getAllSchedulesForCustomer(Customer customer) {
        return scheduleRepository.findAllSchedulesForCustomer(customer);
    }
}

