package com.devpro.shelterBuddyBot.model.shelter;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


import java.util.List;

/**
 * Класс приюта для кошек - CatShelter. Наследник класса {@link AnimalShelter}
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "cat_shelter")
public class CatShelter {
    /**
     * Уникальный идентификатор записи в БД
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
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

    /**
     * Список кошек
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "catShelter_cat",
            joinColumns = @JoinColumn(name = "cat_shelter_id"),
            inverseJoinColumns = @JoinColumn(name = "cat_id"))
    private List<Cat> cats;

    /**
     * Список опекунов кошек
     */
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "catOwner_cat",
            joinColumns = @JoinColumn(name = "cat_owner_id"),
            inverseJoinColumns = @JoinColumn(name = "cat_id"))
    private List<CatOwner> catOwners;

    /**
     * Список рекомендаций от приюта для кошек
     */

    private String dating;
    private String documents;
    private String transportation;
    private String arrangementKitten;
    private String arrangementCat;
    private String arrangementDisabled;

    public CatShelter(String information, String address, String phoneNumber, String workSchedule, String securityContacts, String safetyRecommendations) {
        this.information = information;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.workSchedule = workSchedule;
        this.securityContacts = securityContacts;
        this.safetyRecommendations = safetyRecommendations;
    }

}
