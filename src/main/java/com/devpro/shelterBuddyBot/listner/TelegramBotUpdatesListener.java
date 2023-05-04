package com.devpro.shelterBuddyBot.listner;

import com.devpro.shelterBuddyBot.service.ButtonPressing;
import com.devpro.shelterBuddyBot.service.MessageHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Component
@RequiredArgsConstructor
@Service
public class TelegramBotUpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final TelegramBot telegramBot;
    private final MessageHandler messageHandler;
    private final ButtonPressing buttonPressing;

    @PostConstruct
    public void init() {

        telegramBot.setUpdatesListener(updates -> {
            updates.forEach(this::process);

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    //обрабатываем каждое событие в боте
    public void process(Update update) {
        try {
            SendMessage request = null;

            //события с кнопок InlineKeyboardMarkup
            if (Objects.nonNull(update.callbackQuery())) {
                request = buttonPressing.handleCallback(update.callbackQuery());
            }

            //события с контактом и обычным сообщением
            if (Objects.nonNull(update.message())) {
                if (Objects.nonNull(update.message().contact())) {
                    request = messageHandler.contactProcessing(update.message());
                } else {
                    request = messageHandler.messageProcessing(update.message());
                }
            }

            //выполняет запрос
            if (request != null) {
                telegramBot.execute(request);
            }
        } catch (Exception e) {
            logger.error("Ошибка во время процесса обновления (update): ", e);
        }
    }
}
