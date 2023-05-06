package com.devpro.shelterBuddyBot.service.impl;

import com.devpro.shelterBuddyBot.model.ShelterBuddy;
import com.devpro.shelterBuddyBot.model.enity.ShelterClients;
import com.devpro.shelterBuddyBot.repository.dao.ShelterClientsDao;
import com.devpro.shelterBuddyBot.service.MessageHandler;
import com.devpro.shelterBuddyBot.service.ShelterService;
import com.devpro.shelterBuddyBot.util.CallbackRequest;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Component
@RequiredArgsConstructor
public class MessageHandlerImpl implements MessageHandler {

    private final ShelterClientsDao shelterClientsDao;
    private final ShelterService service;

    @Nullable
    public SendMessage contactProcessing(Message message) {

        long chatId = message.chat().id();

        //обрабатываем отправку контакта и удаляем кнопки ReplyKeyboardRemove
        try {
            // метод репозитория сохранения
            Optional<ShelterBuddy> shelterBuddy = service.getShelterBuddy(chatId);
            shelterBuddy.ifPresent(buddy -> shelterClientsDao.save(ShelterClients.builder()
                    .tookAnimal(false)
                    .name(message.contact().firstName())
                    .number(message.contact().phoneNumber())
                    .shelterBuddy(buddy)
                    .build()));

        } catch (Exception e) {
            return new SendMessage(chatId, "Не удалось записать номер!").replyMarkup(new ReplyKeyboardRemove());
        }
        String userName = " " + message.contact().firstName();
        if (Objects.isNull(message.contact().firstName())) {
            userName = "";
        }

        return new SendMessage(chatId, "Спасибо," + userName + " я записал твой номер! ✅").replyMarkup(new ReplyKeyboardRemove());
    }

    //обрабатываем старт
    @Nullable
    public SendMessage messageProcessing(Message message) {

        long chatId = message.chat().id();

        if ("/start".equals(message.text())) {

            InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
            service.addButton(inlineKeyboard, CallbackRequest.CATS.getName(), CallbackRequest.CATS);
            service.addButton(inlineKeyboard, CallbackRequest.DOGS.getName(), CallbackRequest.DOGS);

            String userName = ", " + message.chat().firstName();
            if (Objects.isNull(message.chat().firstName())) {
                userName = "";
            }

            //Тут берется имя прямо с чата, если будет общий чат будет выводиться имя чата поидеи.
            return new SendMessage(chatId, "Привет" + userName + "!" +
                    "\nЯ расскажу тебе о приютах нашего города и помогу тебе найти или пристроить потеряшку!" +
                    "\n\nКакой приют вы ищите?").replyMarkup(inlineKeyboard);
        } else {
            return new SendMessage(chatId, """
                    Бот не знает такой команды. Введите /start для начала работы с ботом""");
        }
    }
}