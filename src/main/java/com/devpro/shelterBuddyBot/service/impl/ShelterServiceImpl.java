package com.devpro.shelterBuddyBot.service.impl;

import com.devpro.shelterBuddyBot.repository.dao.ChoiceDao;
import com.devpro.shelterBuddyBot.repository.dao.ShelterDao;
import com.devpro.shelterBuddyBot.model.Choice;
import com.devpro.shelterBuddyBot.model.ShelterBuddy;
import com.devpro.shelterBuddyBot.service.ShelterService;
import com.devpro.shelterBuddyBot.util.CallbackRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ShelterServiceImpl implements ShelterService {
    private final ChoiceDao choiceDao;
    private final ShelterDao shelterDao;

    @Override
    public void addButton(InlineKeyboardMarkup inlineKeyboard, String buttonName, CallbackRequest callbackData) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //добавляем кнопку в новую строку addRow и сериализуем объект в json
            inlineKeyboard.addRow(new InlineKeyboardButton(buttonName).callbackData(objectMapper
                    .writeValueAsString(callbackData)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<ShelterBuddy> getShelterBuddy(Long chatId) {
        Optional<ShelterBuddy> shelterBuddy = Optional.empty();
        Optional<Choice> byId = choiceDao.findById(chatId);
        if (byId.isPresent()) {
            CallbackRequest shelter = CallbackRequest.getValueByCode(byId.get().getShelterType());

            if (shelter == CallbackRequest.CATS) {
                shelterBuddy = shelterDao.findById(2);
            } else if (shelter == CallbackRequest.DOGS) {
                shelterBuddy = shelterDao.findById(1);
            }
        }
        return shelterBuddy;
    }

    @Override
    public void showButtonsForDogsCats(InlineKeyboardMarkup inlineKeyboard) {
        addButton(inlineKeyboard, CallbackRequest.SHELTER_INFO.getName(), CallbackRequest.SHELTER_INFO);
        addButton(inlineKeyboard, CallbackRequest.GET_ANIMAL.getName(), CallbackRequest.GET_ANIMAL);
        addButton(inlineKeyboard, CallbackRequest.REPORT_ANIMAL.getName(), CallbackRequest.SHELTER_INFO);
        addButton(inlineKeyboard, CallbackRequest.HELP.getName(), CallbackRequest.HELP);
    }

    @Override
    public void showButtonsForShelterInfo(InlineKeyboardMarkup inlineKeyboard) {
        addButton(inlineKeyboard, CallbackRequest.GET_SHELTER_INFO.getName(), CallbackRequest.GET_SHELTER_INFO);
        addButton(inlineKeyboard, CallbackRequest.SHELTER_CONTACTS.getName(), CallbackRequest.SHELTER_CONTACTS);
        addButton(inlineKeyboard, CallbackRequest.PHONE_SECURITY.getName(), CallbackRequest.PHONE_SECURITY);
        addButton(inlineKeyboard, CallbackRequest.SAFETY_PRECAUTIONS.getName(), CallbackRequest.SAFETY_PRECAUTIONS);
        addButton(inlineKeyboard, CallbackRequest.PUT_MY_PHONE.getName(), CallbackRequest.PUT_MY_PHONE);
        addButton(inlineKeyboard, CallbackRequest.HELP.getName(), CallbackRequest.HELP);
    }
}
