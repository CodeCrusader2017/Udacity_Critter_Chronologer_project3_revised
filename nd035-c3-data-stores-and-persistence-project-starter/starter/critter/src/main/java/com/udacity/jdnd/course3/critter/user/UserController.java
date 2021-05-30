package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    //@Autowired
    //Customer customer;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return  convertCustomerToCustomerDTO(customerService.saveCustomer(convertCustomerDTOToCustomer(customerDTO)));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> custList = customerService.getAllCustomers();
        List<CustomerDTO> custListDTO = new ArrayList<>();

        for (Customer c: custList) {
            custListDTO.add(convertCustomerToCustomerDTO(c));
        }

        return custListDTO;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.findByPet(petId);
        CustomerDTO customerDTO = convertCustomerToCustomerDTO(customer);

        return customerDTO;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee(employeeDTO.getName(), employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        employee.setSkills(employeeDTO.getSkills());
        return convertEmployeeToEmployeeDTO(employeeService.saveEmployee(employee));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.findById(employeeId);
        employeeService.saveEmployee(employee);
        return convertEmployeeToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = employeeService.findById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeService.saveEmployee(employee);
    }

    //Added this method purely as a convenience method when testing on postman
    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO findEmployee(@PathVariable long employeeId) {
        return convertEmployeeToEmployeeDTO(employeeService.findById(employeeId));
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeService.findAllEmployees();
        List<EmployeeDTO> employeesForService = new ArrayList<>();
        LocalDate localDate = employeeDTO.getDate();
        Set<EmployeeSkill> employeeSkills = employeeDTO.getSkills();

        for (Employee employee : employees) {
             if (employee.getDaysAvailable().contains(localDate.getDayOfWeek()) && (employee.getSkills().containsAll(employeeSkills))) {
                 employeesForService.add(convertEmployeeToEmployeeDTO(employee));
             }
        }
        return employeesForService;
    }
    //throw new UnsupportedOperationException();

    //DTO private helper methods
    private CustomerDTO convertCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO, "pets");
        //Convert List of Pets to List of PetIDs
        List<Long> petIds = new ArrayList<>();
        List<Pet> pets = customer.getPets();

        if(pets != null) {
            for (Pet p : pets) {
                petIds.add(p.getId());
            }
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }

    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer, "petIds");
        List<Long>petIds = customerDTO.getPetIds();
        if(petIds != null) {
            List<Pet> pets = petService.getAllPetsByIds(petIds);
            customer.setPets(pets);
        }
        return customer;
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

}
