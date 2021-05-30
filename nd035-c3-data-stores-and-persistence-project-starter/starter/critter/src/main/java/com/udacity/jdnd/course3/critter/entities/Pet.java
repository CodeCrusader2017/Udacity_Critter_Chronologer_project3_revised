package com.udacity.jdnd.course3.critter.entities;

import com.fasterxml.jackson.annotation.JsonView;
import com.udacity.jdnd.course3.critter.pet.PetType;
import org.hibernate.annotations.Nationalized;
import com.udacity.jdnd.course3.critter.entities.Schedule;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Entity
//@Inheritance(strategy = InheritanceType.JOINED)

public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    private PetType type;
    //private String type;

    //@JsonView(Views.Public.class)
    @Nationalized // should use @Nationalized instead of @Type=nstring
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id", nullable=true)
    private Customer customer;

    private LocalDate birthDate;

    @Column(length = 500)
    private String notes;

    @ManyToMany
    @JoinTable(name = "pet_schedule",
            joinColumns = { @JoinColumn(name = "pet_id")},
            inverseJoinColumns = { @JoinColumn(name = "schedule_id")})
    private List<Schedule> schedules;

    @ManyToMany
    @JoinTable(name = "pet_employee",
            joinColumns = { @JoinColumn(name = "pet_id")},
            inverseJoinColumns = { @JoinColumn(name = "employee_id")})
    private List<Employee> employees;

    public Pet() {
    }

    public Pet(PetType type, String name, LocalDate birthDate, String notes) {
        this.type = type;
        this.name = name;
        this.birthDate = birthDate;
        this.notes = notes;
    }

    //Getters and setters
    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}