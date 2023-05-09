package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.model.User;
import com.devpro.shelterBuddyBot.model.enity.*;
import com.devpro.shelterBuddyBot.model.shelter.*;
import com.devpro.shelterBuddyBot.service.impl.ValidationServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class ValidationServiceTest {
    private ValidationService out = new ValidationServiceImpl();

    private static String INFORMATION = "information";
    private static String ADDRES = "address";
    private static String PHONE_NUMBER = "+79053930303";
    private static String WORK_SCHEDULE = "always";
    private static String SECURITY_CONTACTS = "contacts";
    private static String SAFETY_RECOMMENDATION = "recommendation";
    private static byte[] PHOTO = new byte[]{127};
    private static String FOOD_RATION = "foodRation";
    private static String GENERAL_HEALTH = "generalHealth";
    private static String BEHAVIOR_CHANGES = "behaviorChanges";

    private static Cat CAT = new Cat(
            "Barsik",
            5,
            true,
            true,
            StatusAnimal.IN_THE_SHELTER);
    private static Dog DOG = new Dog(
            1L,
            "Tuzik",
            5,
            true,
            true,
            StatusAnimal.IN_THE_SHELTER);
    private static Report REPORT = new Report(
            1L,
            PHOTO,
            FOOD_RATION,
            GENERAL_HEALTH,
            BEHAVIOR_CHANGES);

    private static TrialPeriod TRIAL_PERIOD = new TrialPeriod(
            PHONE_NUMBER,
            "Tuzik",
            LocalDate.now(),
            LocalDate.now(),
            Arrays.asList(REPORT),
            TrialPeriodResult.SUCCESS);

    @Test
    void returnTrueWhenCatIsCorrect() {
        assertTrue(out.validate(CAT));
    }

    @Test
    void returnFalseWhenCatIsNotCorrect() {
        assertFalse(out.validate(new Cat()));
    }

    @Test
    void returnTrueWhenDogIsCorrect() {
        assertTrue(out.validate(DOG));
    }

    @Test
    void returnFalseWhenDogIsNotCorrect() {
        assertFalse(out.validate(new Dog()));
    }

    @Test
    void returnTrueWhenCatShelterIsCorrect() {
        assertTrue(out.validate(
                new CatShelter(
                        INFORMATION,
                        ADDRES,
                        PHONE_NUMBER,
                        WORK_SCHEDULE,
                        SECURITY_CONTACTS,
                        SAFETY_RECOMMENDATION)
        ));
    }

    @Test
    void returnFalseWhenCatShelterIsNotCorrect() {
        assertFalse(out.validate(new CatShelter()));
    }

    @Test
    void returnTrueWhenDogShelterIsCorrect() {
        assertTrue(out.validate(
                new DogShelter(
                        1L,
                        INFORMATION,
                        ADDRES,
                        PHONE_NUMBER,
                        WORK_SCHEDULE,
                        SECURITY_CONTACTS,
                        SAFETY_RECOMMENDATION)
        ));
    }

    @Test
    void returnFalseWhenDogShelterIsNotCorrect() {
        assertFalse(out.validate(new DogShelter()));
    }

    @Test
    void returnTrueWhenCatOwnerIsCorrect() {
        List<Cat> cats = Arrays.asList(CAT);
        List<TrialPeriod> trialPeriods = Arrays.asList(TRIAL_PERIOD);

        assertTrue(out.validate(
                new CatOwner(
                        PHONE_NUMBER,
                        cats,
                        trialPeriods,
                        Arrays.asList(TRIAL_PERIOD))
        ));
    }

    @Test
    void returnFalseWhenCatOwnerIsNotCorrect() {
        assertFalse(out.validate(new CatOwner(
                "",
                Arrays.asList(CAT),
                Arrays.asList(TRIAL_PERIOD),
                Arrays.asList(TRIAL_PERIOD))
        ));
    }

    @Test
    void returnTrueWhenDogOwnerIsCorrect() {
        List<Dog> dogs = Arrays.asList(DOG);
        List<TrialPeriod> trialPeriods = Arrays.asList(TRIAL_PERIOD);

        assertTrue(out.validate(
                new DogOwner(
                        PHONE_NUMBER,
                        dogs,
                        trialPeriods,
                        Arrays.asList(TRIAL_PERIOD))
        ));
    }

    @Test
    void returnFalseWhenDogOwnerIsNotCorrect() {
        assertFalse(out.validate(new DogOwner(
                "",
                Arrays.asList(DOG),
                Arrays.asList(TRIAL_PERIOD),
                Arrays.asList(TRIAL_PERIOD))
        ));
    }

    @Test
    void returnTrueWhenStringIsCorrect() {
        assertTrue(out.validateString(INFORMATION));
    }

    @Test
    void returnFalseWhenStringIsNotCorrect() {
        assertFalse(out.validateString(""));
    }

    @Test
    void returnTrueWhenPhoneNumberIsCorrect() {
        assertTrue(out.validatePhoneNumber(PHONE_NUMBER));
    }

    @Test
    void returnFalseWhenPhoneNumberIsNotCorrect() {
        assertFalse(out.validatePhoneNumber("+790390303"));
    }

    @Test
    void returnTrueWhenMessageToVolunteerIsCorrect() {
        assertTrue(out.validate(new MessageToVolunteer(PHONE_NUMBER,
                "message")));
    }

    @Test
    void returnFalseWhenMessageToVolunteerIsNotCorrect() {
        assertFalse(out.validate(new MessageToVolunteer()));
    }

    @Test
    void returnTrueWhenReportIsCorrect() {
        assertTrue(out.validate(REPORT));
    }

    @Test
    void returnFalseWhenReportIsNotCorrect() {
        assertFalse(out.validate(new Report()));
    }

    @Test
    void returnTrueWhenTrialPeriodIsCorrect() {
        assertTrue(out.validate(TRIAL_PERIOD));
    }

    @Test
    void returnFalseWhenTrialPeriodIsNotCorrect() {
        assertFalse(out.validate(new TrialPeriod()));
    }

    @Test
    void returnTrueWhenUserIsCorrect() {
        assertTrue(out.validate(new User(PHONE_NUMBER)));
    }

    @Test
    void returnFalseWhenUserIsNotCorrect() {
        assertFalse(out.validate(new User()));
    }
}
