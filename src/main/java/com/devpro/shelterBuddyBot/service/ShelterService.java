package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.model.ShelterBuddy;
import com.devpro.shelterBuddyBot.util.CallbackRequest;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

import java.util.Optional;

public interface ShelterService {

    void addButton(InlineKeyboardMarkup inlineKeyboard, String buttonName, CallbackRequest callbackData);

    Optional<ShelterBuddy> getShelterBuddy(Long chatId);

    void showButtonsForDogsCats(InlineKeyboardMarkup inlineKeyboard);

    void showButtonsForShelterInfo(InlineKeyboardMarkup inlineKeyboard);

    void showButtonsForHowTakeDog(InlineKeyboardMarkup inlineKeyboard);

    void showButtonsForHowTakeCat(InlineKeyboardMarkup inlineKeyboard);
}
