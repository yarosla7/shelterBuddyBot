package com.devpro.shelterBuddyBot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AnimalAdvice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String introductionRules;

    private String necessaryDocuments;

    private String transportationRecommendations;

    private String adultHomeArrangement;

    private String homeArrangementWithLimitedAbility;

    private String kittenHomeArrangement;

    private String puppyHomeArrangement;

    private String initialCommunicationWithDogAdvice;

    private String bestDogHandlers;

    private String reasonsForRefusingAnimalTransfer;
}
