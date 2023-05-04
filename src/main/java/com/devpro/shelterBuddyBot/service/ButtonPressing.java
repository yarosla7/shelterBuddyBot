package com.devpro.shelterBuddyBot.service;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;

public interface ButtonPressing {
    SendMessage handleCallback(CallbackQuery callbackQuery);
}
