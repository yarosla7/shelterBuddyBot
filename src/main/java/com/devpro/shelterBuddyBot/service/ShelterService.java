package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.model.ShelterBuddy;
import com.devpro.shelterBuddyBot.util.CallbackRequest;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

import java.util.Optional;

/**
 * Интерфейс ShelterService, который реализует методы для работы с приютами.
 */

public interface ShelterService {

    /**
     * Добавляет кнопку в переданный объект inlineKeyboard.
     *
     * @param inlineKeyboard объект для добавления кнопки
     * @param buttonName     название кнопки
     * @param callbackData   данные, которые будут отправлены при нажатии на кнопку
     */
    void addButton(InlineKeyboardMarkup inlineKeyboard, String buttonName, CallbackRequest callbackData);

    /**
     * Получает приют из базы данных по переданному chatId.
     *
     * @param chatId идентификатор чата, для которого осуществляется поиск приюта
     * @return найденный приют, либо пустой Optional, если приют не найден
     */
    Optional<ShelterBuddy> getShelterBuddy(Long chatId);


    // Метод проверки на совпадение номера телефона и shelter_name, юзера и Юзеров в бд
    Boolean checkClientNumber(Message message);

    Boolean checkClientChatIdInShelter(Message message);

    /**
     * Отображает доступные кнопки для выбора типа приюта (для собак/для кошек).
     *
     * @param inlineKeyboard объект для добавления кнопок
     */
    void showButtonsForDogsCats(InlineKeyboardMarkup inlineKeyboard);

    /**
     * Отображает доступные кнопки для получения информации о приюте.
     *
     * @param inlineKeyboard объект для добавления кнопок
     */

    void showButtonsForShelterInfo(InlineKeyboardMarkup inlineKeyboard);

    /**
     * Отображает доступные кнопки для получения информации о том, как забрать собаку из приюта.
     *
     * @param inlineKeyboard объект для добавления кнопок
     */
    void showButtonsForHowTakeDog(InlineKeyboardMarkup inlineKeyboard);

    /**
     * Отображает доступные кнопки для получения информации о том, как забрать кошку из приюта.
     *
     * @param inlineKeyboard объект для добавления кнопок
     */
    void showButtonsForHowTakeCat(InlineKeyboardMarkup inlineKeyboard);

    String getUserName(String firstName);
}