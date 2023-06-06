/**
 * Интерфейс ButtonPressing определяет метод handleCallback, который должен быть реализован классом,
 * используемым для обработки нажатия кнопок в Telegram-боте ShelterBuddyBot.
 */
package com.devpro.shelterBuddyBot.service;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;

public interface ButtonPressing {
    /**
     * Метод handleCallback должен быть реализован классом, который используется для обработки нажатия
     * кнопок в Telegram-боте ShelterBuddyBot. Данный метод принимает объект CallbackQuery, который представляет
     * информацию о нажатой кнопке, и возвращает объект SendMessage, который содержит сообщение, отправляемое
     * пользователю в ответ на нажатие кнопки.
     *
     * @param callbackQuery Объект CallbackQuery, представляющий информацию о нажатой кнопке
     * @return Объект SendMessage, содержащий сообщение, отправляемое пользователю в ответ на нажатие кнопки
     */
    SendMessage handleCallback(CallbackQuery callbackQuery);
}