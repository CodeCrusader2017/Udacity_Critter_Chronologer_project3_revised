package com.udacity.jdnd.course3.critter.entities;

import javax.persistence.Embeddable;

@Embeddable
public enum PetType {
    CAT, DOG, LIZARD, BIRD, FISH, SNAKE, OTHER;
}