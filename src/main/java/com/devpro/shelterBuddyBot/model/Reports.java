package com.devpro.shelterBuddyBot.model;

import com.devpro.shelterBuddyBot.model.enity.ShelterClients;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@Entity
public class Reports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private ShelterClients shelterClients;

    @ManyToOne
    @JoinColumn(name = "shelter_id", nullable = false)
    private ShelterBuddy shelterBuddy;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate date;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String reportText;

    @Column(name = "telegram_photo_link", nullable = false)
    private String telegramServerLink;
}