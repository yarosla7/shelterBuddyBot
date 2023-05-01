package com.devpro.shelterBuddyBot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "shelter_buddy")
@NoArgsConstructor
@AllArgsConstructor
public class ShelterBuddy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shelterId;

    @Column(name = "shelter_name")
    private String shelterName;

    @Column(name = "address")
    private String address;

    @Column(name = "shelter_phone")
    private String shelterPhone;

    @Column(name = "security_phone")
    private String securityPhone;

    @Column(name = "driving_directions")
    private String drivingDirections;

    @Column(name = "safety_recommendations")
    private String safetyRecommendations;

    @Column(name = "shelter_info")
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
}