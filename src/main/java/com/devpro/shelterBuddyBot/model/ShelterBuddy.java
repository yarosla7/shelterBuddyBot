package com.devpro.shelterBuddyBot.model;

import com.devpro.shelterBuddyBot.model.entity.ShelterClients;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ShelterBuddy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shelterId;

    private String shelterName;

    private String address;

    private String schedule;

    private String shelterPhone;

    private String securityPhone;

    private String drivingDirections;

    private String safetyRecommendations;

    private String shelterInfo;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "shelterBuddy", cascade = CascadeType.ALL)
    private List<ShelterClients> shelterClients;


    public String getContacts() {
        return shelterName + ":\n" +
                "\n\uD83C\uDFE1 Адрес: " + address +
                "\n⏱График работы: " + schedule +
                "\n\uD83D\uDCDE Телефон: " + shelterPhone +
                "\n\uD83D\uDE98 Схема проезда: " + drivingDirections;
    }
}