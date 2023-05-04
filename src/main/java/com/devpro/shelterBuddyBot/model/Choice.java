package com.devpro.shelterBuddyBot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
/* Бд для отслеживания состояния вчате кошки/собаки */
public class Choice {

    @Id
    private Long id;

    private String shelterType;

}
