package com.devpro.shelterBuddyBot.model.enity;

import com.devpro.shelterBuddyBot.model.ShelterBuddy;
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