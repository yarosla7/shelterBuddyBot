package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.model.enity.MessageToVolunteer;

import java.util.List;


/**
 * Сервис для работы с БД сообщений для волонтеров
 */
public interface MessageToVolunteerService {

    MessageToVolunteer createMessageToVolunteer(MessageToVolunteer messageToVolunteer);

    MessageToVolunteer findById(Long id);

    MessageToVolunteer updateById(Long id, MessageToVolunteer messageToVolunteer);

    MessageToVolunteer deleteById(Long id);

    List<MessageToVolunteer> findAll();

    MessageToVolunteer createMessageFromText(String text);

    boolean checker();

    List<String> findAllUnread();
}
