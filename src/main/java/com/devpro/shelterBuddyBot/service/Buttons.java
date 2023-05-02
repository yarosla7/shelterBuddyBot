package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.util.CallbackRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Service;

@Service
public class Buttons {

    public static void addButton(InlineKeyboardMarkup inlineKeyboard, String buttonName, CallbackRequest callbackData) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //добавляем кнопку в новую строку addRow и сериализуем объект в json
            inlineKeyboard.addRow(new InlineKeyboardButton(buttonName).callbackData(objectMapper
                    .writeValueAsString(callbackData)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
