package com.devpro.shelterBuddyBot.model.enity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Класс - испытательный срок для опекуна животного
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "trial_period")
public class TrialPeriod {
    /**
     * Уникальный идентификатор записи в БД
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String ownerName;

    private String animalName;
    /**
     * Дата начала испытательного срока
     */
    private LocalDate startDate;
    /**
     * Дата окончания испытательного срока
     */
    private LocalDate endDate;
    /**
     * Отчеты опекунов животного
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "trial_period_report",
            joinColumns = @JoinColumn(name = "trial_period_id"),
            inverseJoinColumns = @JoinColumn(name = "report_id"))
    private List<Report> reports;
    /**
     * Результат по прохождению испытательного срока опекуном животного
     */
    @Enumerated(value = EnumType.STRING)
    private TrialPeriodResult result;

    public TrialPeriod(String ownerName, String animalName, LocalDate startDate, LocalDate endDate, List<Report> reports, TrialPeriodResult result) {
        this.ownerName = ownerName;
        this.animalName = animalName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reports = reports;
        this.result = result;
    }

    public TrialPeriod(Long id, String owner_name) {
        this.id = id;
        this.ownerName = owner_name;
    }
}
