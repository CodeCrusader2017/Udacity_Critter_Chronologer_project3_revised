package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.CustomerRepository;
import com.udacity.jdnd.course3.critter.data.PetRepository;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).get();
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findByPet(Long petId) {
        Pet pet = petRepository.getOne(petId);
        Customer customer = pet.getCustomer();
        return customer;
    }

    public void savePetForCustomer(Long id, Pet pet) {
        Customer c = customerRepository.findById(id).get();
        pet.setCustomer(c);
        c.getPets().add(pet);
    }

    public Customer getOneCustomer(Long id) {
        return customerRepository.getOne(id);
    }
}
