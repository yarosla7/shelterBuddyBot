package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.model.User;
import com.devpro.shelterBuddyBot.model.enity.MessageToVolunteer;
import com.devpro.shelterBuddyBot.model.enity.Report;
import com.devpro.shelterBuddyBot.model.enity.TrialPeriod;
import com.devpro.shelterBuddyBot.model.shelter.*;

public interface ValidationService {
    /**
     * проверка
     * @param cat
     * @return
     */
    public boolean validate(Cat cat);

    /**
     * проверка
     * @param dog
     * @return
     */
    public boolean validate(Dog dog);

    /**
     * проверка
     * @param catShelter
     * @return
     */

    public boolean validate(CatShelter catShelter);

    /**
     * проверка
     * @param dogShelter
     * @return
     */

    public boolean validate(DogShelter dogShelter);

    /**
     * проверка
     * @param catOwner
     * @return
     */

    public boolean validate(CatOwner catOwner);

    /**
     * проверка
     * @param dogOwner
     * @return
     */

    public boolean validate(DogOwner dogOwner);

    /**
     * проверка
     * @param str
     * @return
     */

    public boolean validateString(String str);

    /**
     * проверка
     * @param phoneNumber
     * @return
     */

    public boolean validatePhoneNumber(String phoneNumber);

    /**
     * проверка
     * @param messageToVolunteer
     * @return
     */
    public boolean validate (MessageToVolunteer messageToVolunteer);

    /**
     * проверка
     * @param report
     * @return
     */
    public boolean validate (Report report);

    /**
     * проверка
     * @param trialPeriod
     * @return
     */
    public boolean validate (TrialPeriod trialPeriod);

    /**
     * проверка
     * @param user
     * @return
     */
    public boolean validate (User user);

}
