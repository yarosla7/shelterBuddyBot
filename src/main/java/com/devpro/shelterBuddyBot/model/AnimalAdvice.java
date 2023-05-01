package com.devpro.shelterBuddyBot.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "animal_advice")
public class AnimalAdvice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "introduction_rules")
    private String introductionRules;
    @Column(name = "necessary_documents")
    private String necessaryDocuments;
    @Column(name = "transportation_recommendations")
    private String transportationRecommendations;
    @Column(name = "adult_home_arrangement")
    private String adultHomeArrangement;
    @Column(name = "home_arrangement_with_limited_ability")
    private String homeArrangementWithLimitedAbility;
    @Column(name = "kitten_home_arrangement")
    private String kittenHomeArrangement;
    @Column(name = "puppy_home_arrangement")
    private String puppyHomeArrangement;
    @Column(name = "initial_communication_with_dog_advice")
    private String initialCommunicationWithDogAdvice;
    @Column(name = "communication_with_grown_dog_advice")
    private String communicationWithGrownDogAdvice;
    @Column(name = "reasons_for_refusing_animal_transfer")
    private String reasonsForRefusingAnimalTransfer;
}