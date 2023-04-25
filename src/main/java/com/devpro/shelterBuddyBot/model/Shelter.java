package com.devpro.shelterBuddyBot.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "shelter")
//@NoArgsConstructor
//@AllArgsConstructor
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shelterId;

    //    @Column(name = "shelter_name")
    private String shelterName;

    //    @Column(name = "address")
    private String address;

    //    @Column(name = "shelter_phone")
    private String shelterPhone;

    //    @Column(name = "security_phone")
    private String securityPhone;

    //    @Column(name = "driving_directions")
    private String drivingDirections;

    //    @Column(name = "safety_recommendations")
    private String safetyRecommendations;


}
