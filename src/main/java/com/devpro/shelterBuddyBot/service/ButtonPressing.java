package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.dao.ShelterDao;
import com.devpro.shelterBuddyBot.listner.TelegramBotUpdatesListener;
import com.devpro.shelterBuddyBot.model.ShelterBuddy;
import com.devpro.shelterBuddyBot.util.CallbackRequest;
import com.devpro.shelterBuddyBot.util.ShelterType;
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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Component
@RequiredArgsConstructor
public class ButtonPressing {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final ShelterModeService shelterModeService;
    private final ShelterDao shelterDao;

    public SendMessage handleCallback(CallbackQuery callbackQuery) {
        Message message = callbackQuery.message();
        long chatId = message.chat().id();
        String data = callbackQuery.data();

        ObjectMapper objectMapper = new ObjectMapper();
        CallbackRequest callbackRequest = null;
        try {
            //десериализуем данные из события бота в объект
            callbackRequest = objectMapper.readValue(data, CallbackRequest.class);
        } catch (JsonProcessingException e) {
            logger.error(String.valueOf(e));
        }

        if (Objects.isNull(callbackRequest)) {
            return new SendMessage(chatId, "Вызываю волонтера!");
        }

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();

        //пробегаем по всем возможным событиям и что-то делаем
        switch (callbackRequest) {
            case DOGS:
            case CATS:
                ShelterType shelterType = ShelterType.getByName(data);
                shelterModeService.setShelter(chatId, shelterType);

                //добавляем кнопки
                Buttons.addButton(inlineKeyboard, CallbackRequest.SHELTER_INFO.getName(), CallbackRequest.SHELTER_INFO);
                Buttons.addButton(inlineKeyboard, CallbackRequest.GET_ANIMAL.getName(), CallbackRequest.GET_ANIMAL);
                Buttons.addButton(inlineKeyboard, CallbackRequest.REPORT_ANIMAL.getName(), CallbackRequest.SHELTER_INFO);
                Buttons.addButton(inlineKeyboard, CallbackRequest.HELP.getName(), CallbackRequest.HELP);

                return new SendMessage(chatId, "Отличный выбор! Чем я могу тебе помочь?").replyMarkup(inlineKeyboard);
            case SHELTER_INFO:

                Buttons.addButton(inlineKeyboard, CallbackRequest.GET_SHELTER_INFO.getName(), CallbackRequest.GET_SHELTER_INFO);
                Buttons.addButton(inlineKeyboard, CallbackRequest.SHELTER_CONTACTS.getName(), CallbackRequest.SHELTER_CONTACTS);
                Buttons.addButton(inlineKeyboard, CallbackRequest.PHONE_SECURITY.getName(), CallbackRequest.PHONE_SECURITY);
                Buttons.addButton(inlineKeyboard, CallbackRequest.SAFETY_PRECAUTIONS.getName(), CallbackRequest.SAFETY_PRECAUTIONS);
                Buttons.addButton(inlineKeyboard, CallbackRequest.PUT_MY_PHONE.getName(), CallbackRequest.PUT_MY_PHONE);
                Buttons.addButton(inlineKeyboard, CallbackRequest.HELP.getName(), CallbackRequest.HELP);

                return new SendMessage(chatId, "Что именно тебя интересует?").replyMarkup(inlineKeyboard);
            case GET_SHELTER_INFO:

                return new SendMessage(chatId, shelterDao.findById(1).get().getShelterInfo());
            case PHONE_SECURITY:
                ShelterType shelter = shelterModeService.getShelter(chatId);
                Optional<ShelterBuddy> byId;

                if (shelter == ShelterType.CATS) {
                    byId = shelterDao.findById(2);
                } else {
                    byId = shelterDao.findById(1);
                }
                if (byId.isPresent()) {

                    return new SendMessage(chatId, "Номер телефона охраны: " + byId.get().getSecurityPhone());
                }
                return new SendMessage(chatId, "Нету!");
            case SAFETY_PRECAUTIONS:
                return new SendMessage(chatId, shelterDao.findById(1).get().getSafetyRecommendations());
            case SHELTER_CONTACTS:
                return new SendMessage(chatId, "Даю тебе контакты приюта!");
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
}
