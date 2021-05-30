package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.time.DayOfWeek;

@Entity
//@Inheritance(strategy = InheritanceType.JOINED)

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@JsonView(Views.Public.class)
    @Nationalized // should use @Nationalized instead of @Type=nstring
    private String name;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skills;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysAvailable;

    public Employee(String name, Set<EmployeeSkill> skills) {
        this.name = name;
        this.skills = skills;
    }

    @ManyToMany
    @JoinTable(name = "employee_pet",
            joinColumns = { @JoinColumn(name = "employee_id")},
            inverseJoinColumns = { @JoinColumn(name = "pet_id")})
    private List<Pet> pets;

    @ManyToMany
    @JoinTable(name = "employee_schedule",
            joinColumns = { @JoinColumn(name = "employee_id")},
            inverseJoinColumns = { @JoinColumn(name = "schedule_id")})
    private List<Schedule> schedules;

    public Employee() {
    }

    //Getters and setters

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }
}
