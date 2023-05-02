package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.dao.ShelterClientsDao;
import com.devpro.shelterBuddyBot.dao.ShelterDao;
import com.devpro.shelterBuddyBot.model.ShelterClients;
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

@Service
@Component
@RequiredArgsConstructor
public class MessageHandler {


    private final ShelterClientsDao shelterClientsDao;
    private final ShelterDao shelterDao;

    @Nullable
    public SendMessage messageProcessing(Message message) {

        long chatId = message.chat().id();

        //обрабатываем отправку контакта и удаляем кнопки ReplyKeyboardRemove
        if (Objects.nonNull(message.contact())) {

            // Записываем контакт. Сейчас взяли или не взяли животное, ввел вручную, и вручную ввел shelter_id
//            ShelterClients shelterClients = new ShelterClients(message.contact().firstName(), message.contact().phoneNumber(), false, shelterDao.findById(1).get());
            ShelterClients shelterClients = new ShelterClients(message.contact().firstName(), message.contact().phoneNumber(), false, shelterDao.findById(1).get());
            // метод репозитория сохранения
            shelterClientsDao.save(shelterClients);
            return new SendMessage(chatId, "Спасибо я записал твой номер!").replyMarkup(new ReplyKeyboardRemove());
        }

        //обрабатываем старт
        if ("/start".equals(message.text())) {

            InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
            Buttons.addButton(inlineKeyboard, CallbackRequest.CATS.getName(), CallbackRequest.CATS);
            Buttons.addButton(inlineKeyboard, CallbackRequest.DOGS.getName(), CallbackRequest.DOGS);

            return new SendMessage(chatId, """
                    Привет я расскажу тебе о приютах нашего города и помогу тебе найти  или пристроить потеряшку!

                    Какой вы ищите приют?""").replyMarkup(inlineKeyboard);
        } else {
            return new SendMessage(chatId, """
                    Бот не знает такой команды. Введите /start для начала работы с ботом""");
        }
    }
}
