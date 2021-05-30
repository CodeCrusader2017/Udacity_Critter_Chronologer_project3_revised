package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entities.*;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.ScheduleController;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    @Autowired
    ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDTOToSchedule(scheduleDTO);
        scheduleService.saveSchedule(schedule);
        return convertScheduleToScheduleDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> listSchedules = scheduleService.getAllSchedules();
        List<ScheduleDTO> listSchedulesDTO = new ArrayList<>();
        for (Schedule schedule : listSchedules) {
            listSchedulesDTO.add(convertScheduleToScheduleDTO(schedule));

        }
        return listSchedulesDTO;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        Pet pet = petService.getPetById(petId);
        List<Schedule> schedule = scheduleService.getAllSchedulesForPet(pet);

        System.out.println("Paul test 1 schedule size = " + schedule.size());
        List<ScheduleDTO> scheduleDTO = new ArrayList<>();
        for (Schedule schedules : schedule) {
            System.out.println("Paul test 2 schedule ID = " + schedules.getId());
            scheduleDTO.add(convertScheduleToScheduleDTO(schedules));
        }
        return scheduleDTO;

    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {

        Employee employee = employeeService.findById(employeeId);
        List<Schedule> schedule = scheduleService.getAllSchedulesForEmployee(employee);
        List<ScheduleDTO> scheduleDTO = new ArrayList<>();
        for (Schedule schedules : schedule) {
            scheduleDTO.add(convertScheduleToScheduleDTO(schedules));
        }
        return scheduleDTO;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {

        List<ScheduleDTO> scheduleDTO = new ArrayList<>();
        List<Pet> pets = petService.getAllPets();

        for (Pet pet : pets) {
            //Only get schedules for the customer ID in question passed in as a parameter.
            if (pet.getCustomer().getId() == customerService.findById(customerId).getId()) {
                List<Schedule> schedule = scheduleService.getAllSchedulesForPet(pet);
                for (Schedule schedules : schedule) {
                    scheduleDTO.add(convertScheduleToScheduleDTO(schedules));
                }
                //System.out.println("DEBUG message: Pet customer = " + pet.getCustomer().getId() + " pet name = " + pet.getName() + " pet ID = " + pet.getId());
            }
        }

        return scheduleDTO;
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule  = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Pet> pets = new ArrayList<>();
        List<Long> petIds = scheduleDTO.getPetIds();
        for (Long petID : petIds) {
            pets.add(petService.getPetById(petID));
        }

        List<Employee> employees = new ArrayList<>();
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        for (Long employeeID : employeeIds) {
            employees.add(employeeService.findById(employeeID));
        }

        schedule.setPets(pets);
        schedule.setEmployees(employees);
        return schedule;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Pet> pets = schedule.getPets();
        List<Long> petIDs = new ArrayList<>();
        for (Pet petID : pets) {
            petIDs.add(petID.getId());
        }

        List<Employee> employees = schedule.getEmployees();
        List<Long> employeeIDs = new ArrayList<>();
        for (Employee employee : employees) {
            employeeIDs.add(employee.getId());
        }

        scheduleDTO.setPetIds(petIDs);
        scheduleDTO.setEmployeeIds(employeeIDs);
        return scheduleDTO;
    }
}

//throw new UnsupportedOperationException();
