package com.devpro.shelterBuddyBot.model.enity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Класс - отчет опекуна животного
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "report")
public class Report {
    /**
     * Уникальный идентификатор записи в БД
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     * Фото животного
     */
    private byte[] photo;
    /**
     * Рацион питания животного
     */
    private String foodRation;
    /**
     * Состояние здоровья животного
     */
    private String generalHealth;
    /**
     * Изменение в поведении животного
     */
    private String behaviorChanges;
    /**
     * Дата отсылки отчета
     */
    private LocalDate receiveDate;
    private Long sender;

    public Report(Long id, byte[] photo, String foodRation, String generalHealth, String behaviorChanges) {
        this.photo = photo;
        this.foodRation = foodRation;
        this.generalHealth = generalHealth;
        this.behaviorChanges = behaviorChanges;
        this.receiveDate = LocalDate.now();
    }

    public Report(byte[] photo, String foodRation, String generalHealth, String behaviorChanges) {
        this.photo = photo;
        this.foodRation = foodRation;
        this.generalHealth = generalHealth;
        this.behaviorChanges = behaviorChanges;
        this.receiveDate = LocalDate.now();
    }

    public Report(byte[] photo, Long sender) {
        this.photo = photo;
        this.sender = sender;
        this.receiveDate = LocalDate.now();
    }
}
