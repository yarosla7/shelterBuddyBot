package com.devpro.shelterBuddyBot.model.util;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
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
