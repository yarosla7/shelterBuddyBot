package com.devpro.shelterBuddyBot.model;

import com.devpro.shelterBuddyBot.model.entity.ShelterClients;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "shelterBuddy", cascade = CascadeType.ALL)
    private List<ShelterClients> shelterClients;

    @Override
    public String toString() {
        return "ShelterBuddy{" +
                "shelterId=" + shelterId +
                ", shelterName='" + shelterName + '\'' +
                ", address='" + address + '\'' +
                ", shelterPhone='" + shelterPhone + '\'' +
                ", securityPhone='" + securityPhone + '\'' +
                ", drivingDirections='" + drivingDirections + '\'' +
                ", safetyRecommendations='" + safetyRecommendations + '\'' +
                ", shelterInfo='" + shelterInfo + '\'' +
                '}';
    }

    public String getContacts() {
        return shelterName + ":\n" +
                "\n\uD83C\uDFE1 Адрес: " + address +
                "\n⏱График работы: " + schedule +
                "\n\uD83D\uDCDE Телефон: " + shelterPhone +
                "\n\uD83D\uDE98 Схема проезда: " + drivingDirections;
    }
}