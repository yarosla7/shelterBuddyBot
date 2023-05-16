package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.model.entity.Animal;
import com.devpro.shelterBuddyBot.model.entity.Reports;
import com.pengrad.telegrambot.model.Message;

import java.util.List;

public interface AdminService {

    void adoptAnimal(Integer animalId, Integer userId);

    List<Reports> showAllReports();

    String countReport();

    String countApplicantAnimals();

    Reports showReports(Message message);

    void addVolunteer(Long chatId, String name);

    Animal showApplicantsAnimals(Message message);
}
