package com.devpro.shelterBuddyBot.service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;

public interface MessageHandler {

    SendMessage contactProcessing(Message message);

    SendMessage messageProcessing(Message message);
}
