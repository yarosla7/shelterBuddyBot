package com.devpro.shelterBuddyBot.listner;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final TelegramBot telegramBot;

    public TelegramBotUpdatesListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try { // Обходим все пришедшие апдейты и выводим их в лог updates.forEach(update -> { logger.info("Handles update: {}", update); });
            // Получаем первое сообщение из списка пришедших апдейтов
            Message message = updates.get(0).message();
            // Получаем ID чата, куда нужно отправлять сообщения
            Long chatId = message.chat().id();
            // Получаем текст этого сообщения
            String text = message.text();

            // Если текст первого сообщения "/start", отправляем приветственное сообщение
            if ("/start".equals(text)){
                // Создаем объект класса SendMessage и передаем ему ID чата и текст сообщения
                SendMessage sendMessage = new SendMessage(chatId, "Hello!");
                // Отправляем сообщение через метод execute объекта класса TelegramBot
                SendResponse sendResponse = telegramBot.execute(sendMessage);
                // Если сообщение отправлено не успешно, выводим ошибку в лог
                if (!sendResponse.isOk()) {
                    logger.error("Error during sending message: {}", sendResponse.description());
                }
            }
        } catch (Exception e) {
            // Если произошла ошибка в процессе обработки сообщения, выводим ее в лог
            logger.error(e.getMessage(), e);
        }
// Возвращаем CONFIRMED_UPDATES_ALL, чтобы сообщить, что все апдейты обработаны
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}