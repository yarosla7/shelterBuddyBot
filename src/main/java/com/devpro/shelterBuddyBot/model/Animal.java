package com.devpro.shelterBuddyBot.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@Entity(name = "animals")
@NoArgsConstructor
@AllArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer animalId;

    private String petName;

    private String breed;

    private Integer petAge;

    private String typeOfAnimal;

    private boolean isInShelter;

    private LocalDate adoptDate;

    private Integer userId;

    private Boolean adopted;
}
