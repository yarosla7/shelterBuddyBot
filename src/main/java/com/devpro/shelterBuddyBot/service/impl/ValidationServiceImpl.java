package com.devpro.shelterBuddyBot.service.impl;

import com.devpro.shelterBuddyBot.model.User;
import com.devpro.shelterBuddyBot.model.enity.MessageToVolunteer;
import com.devpro.shelterBuddyBot.model.enity.Report;
import com.devpro.shelterBuddyBot.model.enity.TrialPeriod;
import com.devpro.shelterBuddyBot.model.shelter.*;
import com.devpro.shelterBuddyBot.service.ValidationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ValidationServiceImpl implements ValidationService {
    private static final Pattern PATTERN_PHONE_NUMBER = Pattern.compile("(^(\\+7)(\\d{10}))$");

    @Override
    public boolean validate(Cat cat) {
        return cat != null
                && cat.getAge() >= 0
                && validateString(cat.getName());

    }

    @Override
    public boolean validate(Dog dog) {
        return dog != null
                && dog.getAge() >= 0
                && validateString(dog.getName());
    }

    @Override
    public boolean validate(CatShelter catShelter) {
        return catShelter != null
                && validateString(catShelter.getInformation())
                && validateString(catShelter.getAddress())
                && validatePhoneNumber(catShelter.getPhoneNumber())
                && validateString(catShelter.getWorkSchedule())
                && validateString(catShelter.getSecurityContacts())
                && validateString(catShelter.getSafetyRecommendations());
    }

    @Override
    public boolean validate(DogShelter dogShelter) {
        return dogShelter != null
                && validateString(dogShelter.getInformation())
                && validateString(dogShelter.getAddress())
                && validatePhoneNumber(dogShelter.getPhoneNumber())
                && validateString(dogShelter.getWorkSchedule())
                && validateString(dogShelter.getSecurityContacts())
                && validateString(dogShelter.getSafetyRecommendations());
    }

    @Override
    public boolean validate(CatOwner catOwner) {
        return catOwner != null
                && validatePhoneNumber(catOwner.getPhoneNumber());
    }

    @Override
    public boolean validate(DogOwner dogOwner) {
        return dogOwner != null
                && validatePhoneNumber(dogOwner.getPhoneNumber());
    }

    @Override
    public boolean validateString(String str) {
        return str != null
                && !StringUtils.isEmpty(str)
                && !StringUtils.isBlank(str);
    }

    @Override
    public boolean validatePhoneNumber(String phoneNumber) {
        return PATTERN_PHONE_NUMBER.matcher(phoneNumber).matches();
    }

    @Override
    public boolean validate (MessageToVolunteer messageToVolunteer) {
        return messageToVolunteer != null
                && validateString(messageToVolunteer.getSender())
                && validateString(messageToVolunteer.getText());
    }
    @Override
    public boolean validate (Report report){
        return report != null
                && report.getPhoto() != null
                && validateString(report.getFoodRation())
                && validateString(report.getGeneralHealth())
                && validateString(report.getBehaviorChanges());
    }

    @Override
    public boolean validate(TrialPeriod trialPeriod) {
        return trialPeriod != null
                && validateString(trialPeriod.getOwnerName())
                && trialPeriod.getStartDate() != null
                && trialPeriod.getEndDate() != null
                && trialPeriod.getReports() != null
                && trialPeriod.getResult() != null;
    }

    @Override
    public boolean validate(User user) {
        return user != null
                && validateString(user.getPhoneNumber());
    }

}
