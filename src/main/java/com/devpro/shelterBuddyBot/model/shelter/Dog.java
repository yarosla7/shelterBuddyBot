package com.devpro.shelterBuddyBot.model.shelter;

import com.devpro.shelterBuddyBot.model.enity.StatusAnimal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Класс - собака
 */
@Data
@Entity
@Table(name = "dog")
@NoArgsConstructor
@AllArgsConstructor
public class Dog {

    /**
     * Уникальный идентификатор записи в БД
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     * Имя животного
     */
    private String name;
    /**
     * Возраст животного
     */
    private int age;
    /**
     * Состояние здоровья животного
     */
    private boolean healthStatus;
    /**
     * Статус о вакцинации животного
     */
    private boolean vaccination;
    /**
     * Статус нахождения животного в приюте
     */
    @Enumerated(value = EnumType.STRING)
    private StatusAnimal statusAnimal;

    public Dog(String name, int age, boolean healthStatus, boolean vaccination, StatusAnimal statusAnimal) {
        this.name = name;
        this.age = age;
        this.healthStatus = healthStatus;
        this.vaccination = vaccination;
        this.statusAnimal = statusAnimal;
    }
}
