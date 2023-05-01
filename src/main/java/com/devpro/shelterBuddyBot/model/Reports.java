package com.devpro.shelterBuddyBot.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "reports")
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

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "report_text", columnDefinition = "TEXT", nullable = false)
    private String reportText;

    @Column(name = "telegram_photo_link", nullable = false)
    private String telegramServerLink;
}