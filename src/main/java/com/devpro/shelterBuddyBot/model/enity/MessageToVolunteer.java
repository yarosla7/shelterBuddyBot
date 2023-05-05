package com.devpro.shelterBuddyBot.model.enity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *  Класс - сообщение волонтеру
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "message_to_volunteer")
public class MessageToVolunteer {
    /**
     * Уникальный идентификатор записи в БД
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     * Информация о потенциальном опекуне животного
     */
    private String sender;
    /**
     * Текст сообщения
     */
    private String text;
    /**
     * Дата сообщения
     */
    private LocalDateTime localDateTime;

    @Enumerated(value = EnumType.STRING)
    private StatusMessage statusMessage;

    public MessageToVolunteer(Long id, String sender, String text, LocalDateTime localDateTime) {
        this.id = id;
        this.sender = sender;
        this.text = text;
        this.localDateTime = LocalDateTime.now();
        this.statusMessage = StatusMessage.UNREAD;
    }

    public MessageToVolunteer(Long id, String sender, String text) {
        this.id = id;
        this.sender = sender;
        this.text = text;
        this.localDateTime = LocalDateTime.now();
        this.statusMessage = StatusMessage.UNREAD;
    }

    public MessageToVolunteer(String sender, String text) {
        this.sender = sender;
        this.text = text;
        this.localDateTime = LocalDateTime.now();
        this.statusMessage = StatusMessage.UNREAD;
    }

}
