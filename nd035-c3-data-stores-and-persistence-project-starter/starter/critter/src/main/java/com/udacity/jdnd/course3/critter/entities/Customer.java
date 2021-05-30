package com.udacity.jdnd.course3.critter.entities;

import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.util.List;

@Entity
//@Inheritance(strategy = InheritanceType.JOINED)

public class Customer {
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@JsonView(Views.Public.class)
    @Nationalized // should use @Nationalized instead of @Type=nstring
    private String name;
    @Nationalized
    private String phoneNumber;

    @Column(length = 500)
    private String notes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Pet> pets;

    @ManyToMany
    @JoinTable(name = "customer_schedule",
            joinColumns = { @JoinColumn(name = "customer_id")},
            inverseJoinColumns = { @JoinColumn(name = "schedule_id")})
    private List<Schedule> schedules;

    public Customer(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Customer() {
    }

    //getters and setters

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

//Notes:
//FetchType.EAGER: Always retrieve the associated values as part of the Entity retrieval. This means
//                 the initial query for the entity retrieves this data.
//                 Default for @ManyToOne & @OneToOne.

//FetchType.LAZY:  Wait to retrieve associated values until they are referenced. Lazy-loaded attributes
//                 are Hibernate proxy objects whose specific values are retrieved from the database only
//                 if they’re accessed. The initial query for the entity will NOT retrieve this data.
//                 Default for @OneToMany & @ManyToMany.
//Q: Which FetchType should I use if I always want to load my list of outfits immediately when retrieving a Person Entity?
//A: FetchType.EAGER.

//cascade = CascadeType.ALL:   CascadeType allows us to modify Entity associations so that persistence
//                             operations on one Entity will cascade to other Entities associated with it.
//                             Valid CascadeTypes correspond to the different persistence operations, such
//                             as Persist, Merge, and Remove. See the Hibernate cascade documentation for
//                             more details.
//                             CascadeType.ALL, CascadeType.FIND, CascadeType.PERSIST, CascadeType.REMOVE.
//Q: Which CascadeType should you use to add new rows for associated Entities?
//A: CascadeType.PERSIST.

