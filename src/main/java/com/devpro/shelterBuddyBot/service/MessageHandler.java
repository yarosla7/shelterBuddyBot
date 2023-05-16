package com.devpro.shelterBuddyBot.service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.lang.Nullable;

/**
 * Интерфейс для обработки сообщений Telegram бота.
 */
public interface MessageHandler {

    /**
     * Обрабатывает отправленный контакт пользователя и сохраняет его в базу данных.
     *
     * @param message сообщение пользователя.
     * @return ответное сообщение пользователю или null.
     */
    @Nullable
    SendMessage contactProcessing(Message message);

    /**
     * Обрабатывает стартовое сообщение и возвращает клавиатуру с выбором приюта для поиска.
     *
     * @param message сообщение пользователя.
     * @return ответное сообщение пользователю или null.
     */
    @Nullable
    SendMessage messageProcessing(Message message);

    SendMessage reportProcessing(Message message);
}