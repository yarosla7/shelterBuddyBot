package com.devpro.shelterBuddyBot.model.entity;

import com.devpro.shelterBuddyBot.model.ShelterBuddy;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
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

    private Integer chatId;


    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private ShelterBuddy shelterBuddy;

}