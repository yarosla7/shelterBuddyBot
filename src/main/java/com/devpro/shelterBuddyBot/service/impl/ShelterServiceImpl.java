package com.devpro.shelterBuddyBot.service.impl;

import com.devpro.shelterBuddyBot.model.Choice;
import com.devpro.shelterBuddyBot.model.ShelterBuddy;
import com.devpro.shelterBuddyBot.model.entity.ShelterClients;
import com.devpro.shelterBuddyBot.repository.dao.ChoiceDao;
import com.devpro.shelterBuddyBot.repository.dao.ShelterClientsDao;
import com.devpro.shelterBuddyBot.repository.dao.ShelterDao;
import com.devpro.shelterBuddyBot.service.ShelterService;
import com.devpro.shelterBuddyBot.util.CallbackRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShelterServiceImpl implements ShelterService {
    private final ChoiceDao choiceDao;
    private final ShelterDao shelterDao;
    private final ShelterClientsDao shelterClientsDao;

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
    // Метод проверки на совпадение номера телефона и shelter_name, юзера и Юзеров в бд
    public Boolean checkClientNumber(Message message) {
        boolean checkNumber = true;
        long chatId = message.chat().id();
        Optional<ShelterBuddy> shelterBuddy = getShelterBuddy(chatId);

        if (shelterBuddy.isPresent()) {
            Optional<ShelterClients> shelterClient = shelterClientsDao
                    .findByShelterBuddyAndNumberLike(shelterBuddy.get(), "%" + message.contact().phoneNumber());
            if (shelterClient.isPresent()) {
                checkNumber = false;
            }
        }
        return checkNumber;
    }

    @Override
    public Boolean checkClientChatIdInShelter(Message message) {
        boolean checkChatId = true;
        int chatId = Math.toIntExact(message.chat().id());
        Optional<ShelterBuddy> shelterBuddy = getShelterBuddy((long) chatId);

        if (shelterBuddy.isPresent()) {
            Optional<ShelterClients> shelterClient = shelterClientsDao.findByChatIdAndShelterBuddy(chatId, shelterBuddy.get());
            if (shelterClient.isPresent()) {
                checkChatId = false;
            }
        }
        return checkChatId;
    }

    @Override
    public void showButtonsForDogsCats(InlineKeyboardMarkup inlineKeyboard) {
        addButton(inlineKeyboard, CallbackRequest.STEP_BACK_CHOOSING_SHELTER.getName(), CallbackRequest.STEP_BACK_CHOOSING_SHELTER);
        addButton(inlineKeyboard, CallbackRequest.SHELTER_INFO.getName(), CallbackRequest.SHELTER_INFO);
        addButton(inlineKeyboard, CallbackRequest.GET_ANIMAL.getName(), CallbackRequest.GET_ANIMAL);
        addButton(inlineKeyboard, CallbackRequest.REPORT_ANIMAL.getName(), CallbackRequest.REPORT_ANIMAL);
        addButton(inlineKeyboard, CallbackRequest.HELP.getName(), CallbackRequest.HELP);
    }

    @Override
    public void showButtonsForShelterInfo(InlineKeyboardMarkup inlineKeyboard) {
        addButton(inlineKeyboard, CallbackRequest.STEP_BACK_INFO_SHELTER.getName(), CallbackRequest.STEP_BACK_INFO_SHELTER);
        addButton(inlineKeyboard, CallbackRequest.GET_SHELTER_INFO.getName(), CallbackRequest.GET_SHELTER_INFO);
        addButton(inlineKeyboard, CallbackRequest.SHELTER_CONTACTS.getName(), CallbackRequest.SHELTER_CONTACTS);
        addButton(inlineKeyboard, CallbackRequest.PHONE_SECURITY.getName(), CallbackRequest.PHONE_SECURITY);
        addButton(inlineKeyboard, CallbackRequest.SAFETY_PRECAUTIONS.getName(), CallbackRequest.SAFETY_PRECAUTIONS);
        addButton(inlineKeyboard, CallbackRequest.PUT_MY_PHONE.getName(), CallbackRequest.PUT_MY_PHONE);
        addButton(inlineKeyboard, CallbackRequest.HELP.getName(), CallbackRequest.HELP);
    }

    @Override
    public void showButtonsForHowTakeDog(InlineKeyboardMarkup inlineKeyboard) {
        addButton(inlineKeyboard, CallbackRequest.STEP_BACK_INFO_SHELTER.getName(), CallbackRequest.STEP_BACK_INFO_SHELTER);
        addButton(inlineKeyboard, CallbackRequest.INTRODUCTION_RULES.getName(), CallbackRequest.INTRODUCTION_RULES);
        addButton(inlineKeyboard, CallbackRequest.NECESSARY_DOCUMENTS.getName(), CallbackRequest.NECESSARY_DOCUMENTS);
        addButton(inlineKeyboard, CallbackRequest.TRANSPORTATION_RECOMMENDATIONS.getName(), CallbackRequest.TRANSPORTATION_RECOMMENDATIONS);
        addButton(inlineKeyboard, CallbackRequest.PUPPY_HOME_ARRANGEMENT.getName(), CallbackRequest.PUPPY_HOME_ARRANGEMENT);
        addButton(inlineKeyboard, CallbackRequest.ADULT_HOME_ARRANGEMENT.getName(), CallbackRequest.ADULT_HOME_ARRANGEMENT);
        addButton(inlineKeyboard, CallbackRequest.HOME_ARRANGEMENT_WITH_LIMITED_ABILITY.getName(), CallbackRequest.HOME_ARRANGEMENT_WITH_LIMITED_ABILITY);
        addButton(inlineKeyboard, CallbackRequest.INITIAL_COMMUNICATION_WITH_DOG_ADVICE.getName(), CallbackRequest.INITIAL_COMMUNICATION_WITH_DOG_ADVICE);
        addButton(inlineKeyboard, CallbackRequest.BEST_DOG_HANDLERS.getName(), CallbackRequest.BEST_DOG_HANDLERS);
        addButton(inlineKeyboard, CallbackRequest.REASONS_FOR_REFUSING_ANIMAL_TRANSFER.getName(), CallbackRequest.REASONS_FOR_REFUSING_ANIMAL_TRANSFER);
        addButton(inlineKeyboard, CallbackRequest.PUT_MY_PHONE.getName(), CallbackRequest.PUT_MY_PHONE);
        addButton(inlineKeyboard, CallbackRequest.HELP.getName(), CallbackRequest.HELP);
    }

    @Override
    public void showButtonsForHowTakeCat(InlineKeyboardMarkup inlineKeyboard) {
        addButton(inlineKeyboard, CallbackRequest.STEP_BACK_INFO_SHELTER.getName(), CallbackRequest.STEP_BACK_INFO_SHELTER);
        addButton(inlineKeyboard, CallbackRequest.INTRODUCTION_RULES.getName(), CallbackRequest.INTRODUCTION_RULES);
        addButton(inlineKeyboard, CallbackRequest.NECESSARY_DOCUMENTS.getName(), CallbackRequest.NECESSARY_DOCUMENTS);
        addButton(inlineKeyboard, CallbackRequest.TRANSPORTATION_RECOMMENDATIONS.getName(), CallbackRequest.TRANSPORTATION_RECOMMENDATIONS);
        addButton(inlineKeyboard, CallbackRequest.KITTEN_HOME_ARRANGEMENT.getName(), CallbackRequest.KITTEN_HOME_ARRANGEMENT);
        addButton(inlineKeyboard, CallbackRequest.ADULT_HOME_ARRANGEMENT.getName(), CallbackRequest.ADULT_HOME_ARRANGEMENT);
        addButton(inlineKeyboard, CallbackRequest.HOME_ARRANGEMENT_WITH_LIMITED_ABILITY.getName(), CallbackRequest.HOME_ARRANGEMENT_WITH_LIMITED_ABILITY);
        addButton(inlineKeyboard, CallbackRequest.REASONS_FOR_REFUSING_ANIMAL_TRANSFER.getName(), CallbackRequest.REASONS_FOR_REFUSING_ANIMAL_TRANSFER);
        addButton(inlineKeyboard, CallbackRequest.PUT_MY_PHONE.getName(), CallbackRequest.PUT_MY_PHONE);
        addButton(inlineKeyboard, CallbackRequest.HELP.getName(), CallbackRequest.HELP);
    }

    @Override
    public String getUserName(String firstName) {
        String userName = ", " + firstName;
        if (Objects.isNull(firstName)) {
            userName = "";
        }
        return userName;
    }
}
