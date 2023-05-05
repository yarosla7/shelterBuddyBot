package com.devpro.shelterBuddyBot.model.shelter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Базовый класс приюта для животных
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalShelter {

    /**
     * Уникальный идентификатор записи в БД
     */
    private Long id;

    /**
     *  "Информация о питомнике
     */
    private String information;

    /**
     * Адрес питомника
     */
    private String address;

    /**
     * Номер телефона питомника
     */
    private String phoneNumber;

    /**
     * Режим работы питомника
     */
    private String workSchedule;

    /**
     * Контакты охраны питомника
     */
    private String securityContacts;

    /**
     * Рекомендации по технике безопасности на территории питомника
     */
    private String safetyRecommendations;

    public AnimalShelter(Long id) {
        this.id = id;
    }

}
