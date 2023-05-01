package com.devpro.shelterBuddyBot.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "shelter_clients")
public class ShelterClients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String name;

    private String number;

    private Boolean took_animal;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private ShelterBuddy shelterBuddy;

    @OneToOne
    @JoinColumn(name = "animal_id", foreignKey = @ForeignKey(name = "animal_id"))
    private Animal animal;

    public ShelterClients(String name, String number, Boolean took_animal, ShelterBuddy shelterBuddy) {
        this.name = name;
        this.number = number;
        this.took_animal = took_animal;
        this.shelterBuddy = shelterBuddy;
    }

    public ShelterClients(String name, String number, Boolean took_animal) {
        this.name = name;
        this.number = number;
        this.took_animal = took_animal;
    }
    public ShelterClients() {

    }

    @Override
    public String toString() {
        return "ShelterClients{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", took_animal=" + took_animal +
                ", shelterBuddy=" + (shelterBuddy != null ? shelterBuddy.getShelterName() : "не установлен") +
                '}';
    }
}