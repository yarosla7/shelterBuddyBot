package com.devpro.shelterBuddyBot.service.impl;

import com.devpro.shelterBuddyBot.dao.ChoiceDao;
import com.devpro.shelterBuddyBot.listner.TelegramBotUpdatesListener;
import com.devpro.shelterBuddyBot.model.Choice;
import com.devpro.shelterBuddyBot.model.ShelterBuddy;
import com.devpro.shelterBuddyBot.service.ButtonPressing;
import com.devpro.shelterBuddyBot.service.ShelterService;
import com.devpro.shelterBuddyBot.util.CallbackRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ButtonPressingImpl implements ButtonPressing {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final ShelterService service;
    private final ChoiceDao choiceDao;

    @Override
    public SendMessage handleCallback(CallbackQuery callbackQuery) {
        Message message = callbackQuery.message();
        long chatId = message.chat().id();
        String data = callbackQuery.data();
        Optional<ShelterBuddy> shelterBuddy;

        CallbackRequest callbackRequest = getCallbackRequest(data);

        if (Objects.isNull(callbackRequest)) {
            return new SendMessage(chatId, "Вызываю волонтера!");
        }

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();

        //пробегаем по всем возможным событиям и что-то делаем
        switch (callbackRequest) {
            case DOGS:
            case CATS:
                choiceDao.save(Choice.builder()
                        .id(chatId)
                        .shelterType(callbackRequest.getCode())
                        .build());

                //добавляем кнопки
                service.showButtonsForDogsCats(inlineKeyboard);

                return new SendMessage(chatId, "Отличный выбор! Чем я могу тебе помочь?").replyMarkup(inlineKeyboard);
            case SHELTER_INFO:
                service.showButtonsForShelterInfo(inlineKeyboard);
                return new SendMessage(chatId, "Что именно тебя интересует?").replyMarkup(inlineKeyboard);
            case GET_SHELTER_INFO:
                shelterBuddy = service.getShelterBuddy(chatId);
                if (shelterBuddy.isPresent()) {
                    return new SendMessage(chatId, shelterBuddy.get().getShelterInfo());
                }
            case PHONE_SECURITY:
                shelterBuddy = service.getShelterBuddy(chatId);
                if (shelterBuddy.isPresent()) {
                    return new SendMessage(chatId, "Номер телефона охраны: " + shelterBuddy.get().getSecurityPhone());
                }
            case SAFETY_PRECAUTIONS:
                shelterBuddy = service.getShelterBuddy(chatId);
                if (shelterBuddy.isPresent()) {
                    return new SendMessage(chatId, shelterBuddy.get().getSafetyRecommendations());
                }
            case SHELTER_CONTACTS:
                shelterBuddy = service.getShelterBuddy(chatId);
                if (shelterBuddy.isPresent()) {
                    return new SendMessage(chatId, shelterBuddy.get().getContacts());
                }
            case PUT_MY_PHONE:
                Keyboard keyboard = new ReplyKeyboardMarkup(new KeyboardButton("Отдать свой номер телефона").requestContact(true));
                return new SendMessage(chatId, "Забираю у тебя номер телефона!").replyMarkup(keyboard);
            case GET_ANIMAL:
                return new SendMessage(chatId, "Рассказываю тебе как взять животное!");
            case REPORT_ANIMAL:
                return new SendMessage(chatId, "Присылаю отчет о питомце!");
            case HELP:
            default:
                return new SendMessage(chatId, "Вызываю волонтера!");
        }
    }

    private CallbackRequest getCallbackRequest(String data) {
        CallbackRequest callbackRequest = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            //десериализуем данные из события бота в объект
            callbackRequest = objectMapper.readValue(data, CallbackRequest.class);
        } catch (JsonProcessingException e) {
            logger.error(String.valueOf(e));
        }

        return callbackRequest;
    }
}
