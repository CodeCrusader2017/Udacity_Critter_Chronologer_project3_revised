package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = petService.savePet(convertPetDTOToPet(petDTO));
        //Update the customer with pet details.
        Customer customer = customerService.getOneCustomer(petDTO.getOwnerId());
        if (customer.getPets() != null) {
            if (!customer.getPets().contains(pet)) {
                customer.getPets().add(pet);
                customerService.saveCustomer(customer);
            }
        }
        else {
            List<Pet> petList = new ArrayList<>();
            petList.add(pet);
            customer.setPets(petList);
            customerService.saveCustomer(customer);
        }

        return convertPetToPetDTO(pet);
    }

    @PostMapping("/{customerId}")
    public PetDTO getEmployee(@RequestBody PetDTO petDTO, @PathVariable long customerId) {
        Pet pet = petService.savePet(convertPetDTOToPet(petDTO));
        petDTO.setId(pet.getId());

        return petDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        return convertPetToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        List<PetDTO> petsDTO = new ArrayList<>();

        for (Pet p : pets) {
            petsDTO.add(convertPetToPetDTO(p));
        }
        return petsDTO;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        Customer customer = customerService.findById(ownerId);
        List<Pet> pets = customer.getPets();
        List<PetDTO> petsDTO = new ArrayList<>();

        for (Pet p : pets) {
            petsDTO.add(convertPetToPetDTO(p));
        }

        return petsDTO;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet, "customer_Id");
        Long userId = petDTO.getOwnerId();
        Customer customer = customerService.getOneCustomer(userId);
        pet.setCustomer(customer);

        return pet;
    }

    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());  //Set owner ID manually as BeanUtils can't call Id on Customer
        return petDTO;
    }
}
