package com.devpro.shelterBuddyBot.model.shelter;

import com.devpro.shelterBuddyBot.model.enity.ShelterClients;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "animals")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int animalId;

    private String petName;
    private String breed;
    private int petAge;
    private boolean isInShelter;
    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_id"))
    private ShelterClients shelterClients;
}