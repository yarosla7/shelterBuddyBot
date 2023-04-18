package com.devpro.shelterBuddyBot.listner;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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


    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> logger.info("Handles update: {}", update));

            Message message = updates.get(0).message();
            Long chatId = message.chat().id();
            String text = message.text();

//            List<InlineKeyboardButton> buttons =  new ArrayList<>();
//
//            buttons.add(InlineKeyboardButton.)

            InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                    new InlineKeyboardButton("Приют для собак").callbackData("собака"),
                    new InlineKeyboardButton("Приют для кошек").callbackData("кот"));


            if ("/start".equals(text)) {
                SendMessage sendMessage = new SendMessage(chatId,
                        "Привет! Я бот помощник. Помогу вам с приютами, выберите какой приют вам нужен: ").replyMarkup(inlineKeyboard);  // описание изменить, это сделал для себя

                SendResponse sendResponse = telegramBot.execute(sendMessage);


                if (!sendResponse.isOk()) {
                    logger.error("Error during sending message: {}", sendResponse.description());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
