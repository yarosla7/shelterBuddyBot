package com.devpro.shelterBuddyBot.model.entity;

import com.devpro.shelterBuddyBot.model.ShelterBuddy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean isReportOk;

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
    private String telegramPhotoLink;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

}