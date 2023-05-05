package com.devpro.shelterBuddyBot.model.shelter;


import com.devpro.shelterBuddyBot.model.enity.TrialPeriod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.util.List;

/**
 *  Класс - опекун кошки. Наследник класса {@link SecurityProperties.User}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cat_owner")
public class CatOwner {

    /**
     * Уникальный идентификатор записи в БД
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String phoneNumber;

    /**
     * Список кошек
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "cat_owner_cat",
            joinColumns = @JoinColumn(name = "cat_owner_id"),
            inverseJoinColumns = @JoinColumn(name = "cat_id"))
    private List<Cat> cats;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cat_owner_trial_period",
            joinColumns = @JoinColumn(name = "cat_owner_id"),
            inverseJoinColumns = @JoinColumn(name = "trial_period_id"))
    private List<TrialPeriod> trialPeriodsInActiveStatus;
    /**
     * Завершенные периоды испытательного срока для опекуна животного
     */
    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "cat_owner_completed_trial_period",
            joinColumns = @JoinColumn(name = "cat_owner_id"),
            inverseJoinColumns = @JoinColumn(name = "trial_period_id"))
    private List<TrialPeriod> trialPeriodsCompleted;

    public CatOwner(String phoneNumber, List<Cat> cats, List<TrialPeriod> trialPeriodsInActiveStatus, List<TrialPeriod> trialPeriodsCompleted) {
        this.phoneNumber = phoneNumber;
        this.cats = cats;
        this.trialPeriodsInActiveStatus = trialPeriodsInActiveStatus;
        this.trialPeriodsCompleted = trialPeriodsCompleted;
    }
}
