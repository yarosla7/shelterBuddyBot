package com.devpro.shelterBuddyBot.model.shelter;

import com.devpro.shelterBuddyBot.model.enity.TrialPeriod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.util.List;

/**
 *  Класс - опекун собаки. Наследник класса {@link SecurityProperties.User}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dog_owner")
public class DogOwner {

    /**
     * Уникальный идентификатор записи в БД
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String phoneNumber;

    /**
     * Список собак
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "dog_owner_dog",
            joinColumns = @JoinColumn(name = "dog_owner_id"),
            inverseJoinColumns = @JoinColumn(name = "dog_id"))
    private List<Dog> dogs;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "dog_owner_trial_period",
            joinColumns = @JoinColumn(name = "dog_owner_id"),
            inverseJoinColumns = @JoinColumn(name = "trial_period_id"))
    private List<TrialPeriod> trialPeriodsInActiveStatus;
    /**
     * Завершенные периоды испытательного срока для опекуна животного
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "dog_owner_completed_trial_period",
            joinColumns = @JoinColumn(name = "dog_owner_id"),
            inverseJoinColumns = @JoinColumn(name = "trial_period_id"))
    private List<TrialPeriod> trialPeriodsCompleted;

    public DogOwner(String phoneNumber, List<Dog> dogs, List<TrialPeriod> trialPeriodsInActiveStatus, List<TrialPeriod> trialPeriodsCompleted) {
        this.phoneNumber = phoneNumber;
        this.dogs = dogs;
        this.trialPeriodsInActiveStatus = trialPeriodsInActiveStatus;
        this.trialPeriodsCompleted = trialPeriodsCompleted;
    }
}
