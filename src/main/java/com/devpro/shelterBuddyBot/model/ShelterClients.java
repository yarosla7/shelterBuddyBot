package com.devpro.shelterBuddyBot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShelterClients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String name;

    private String number;

    private Boolean tookAnimal;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private ShelterBuddy shelterBuddy;

    @OneToOne
    @JoinColumn(name = "animal_id", foreignKey = @ForeignKey(name = "animal_id"))
    private Animal animal;

    @Override
    public String toString() {
        return "ShelterClients{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", took_animal=" + tookAnimal +
                ", shelterBuddy=" + (shelterBuddy != null ? shelterBuddy.getShelterName() : "не установлен") +
                '}';
    }
}