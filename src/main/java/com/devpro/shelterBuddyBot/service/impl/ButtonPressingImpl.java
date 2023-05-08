package com.devpro.shelterBuddyBot.service.impl;

import com.devpro.shelterBuddyBot.bot.listner.TelegramBotUpdatesListener;
import com.devpro.shelterBuddyBot.model.AnimalAdvice;
import com.devpro.shelterBuddyBot.model.Choice;
import com.devpro.shelterBuddyBot.model.ShelterBuddy;
import com.devpro.shelterBuddyBot.repository.dao.AnimalAdviceDao;
import com.devpro.shelterBuddyBot.repository.dao.ChoiceDao;
import com.devpro.shelterBuddyBot.service.ButtonPressing;
import com.devpro.shelterBuddyBot.service.ShelterService;
import com.devpro.shelterBuddyBot.util.CallbackRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ButtonPressingImpl implements ButtonPressing {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final ShelterService service;
    private final ChoiceDao choiceDao;
    private final AnimalAdviceDao animalAdviceDao;

    @Override
    public SendMessage handleCallback(CallbackQuery callbackQuery) {
        Message message = callbackQuery.message();
        long chatId = message.chat().id();
        String data = callbackQuery.data();
        Optional<ShelterBuddy> shelterBuddy;
        Optional<AnimalAdvice> animalAdvice = animalAdviceDao.findById(1);
        CallbackRequest callbackRequest = getCallbackRequest(data);
        Optional<Choice> choice = choiceDao.findById(chatId);

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();

        //пробегаем по всем возможным событиям и что-то делаем

        switch (callbackRequest) {
            case DOGS:
            case CATS:
                choiceDao.save(Choice.builder()
                        .id(chatId)
                        .shelterType(callbackRequest.getCode())
                        .build());
                choice = choiceDao.findById(chatId);
                //добавляем кнопки
                service.showButtonsForDogsCats(inlineKeyboard);

                if (choice.isPresent()) {
                    if (callbackRequest == CallbackRequest.DOGS) {
                        return new SendMessage(chatId, "Выбран приют для собак, чем я могу тебе помочь?\uD83D\uDC15").replyMarkup(inlineKeyboard);
                    } else {
                        return new SendMessage(chatId, "Выбран приют для кошек, чем я могу тебе помочь?\uD83D\uDC08\u200D⬛").replyMarkup(inlineKeyboard);
                    }
                }
            case SHELTER_INFO:
                service.showButtonsForShelterInfo(inlineKeyboard);
                return new SendMessage(chatId, "Что именно тебя интересует?").replyMarkup(inlineKeyboard);
            case GET_SHELTER_INFO:
                shelterBuddy = service.getShelterBuddy(chatId);
                if (shelterBuddy.isPresent()) {
                    return new SendMessage(chatId, shelterBuddy.get().getShelterInfo());
                }
            case PHONE_SECURITY:
                shelterBuddy = service.getShelterBuddy(chatId);
                if (shelterBuddy.isPresent()) {
                    return new SendMessage(chatId, "Номер телефона охраны: " + shelterBuddy.get().getSecurityPhone());
                }
            case SAFETY_PRECAUTIONS:
                shelterBuddy = service.getShelterBuddy(chatId);
                if (shelterBuddy.isPresent()) {
                    return new SendMessage(chatId, shelterBuddy.get().getSafetyRecommendations());
                }
            case SHELTER_CONTACTS:
                shelterBuddy = service.getShelterBuddy(chatId);
                if (shelterBuddy.isPresent()) {
                    return new SendMessage(chatId, shelterBuddy.get().getContacts());
                }
            case PUT_MY_PHONE:
                Keyboard keyboard = new ReplyKeyboardMarkup(new KeyboardButton("Поделиться номером телефона").requestContact(true));
                return new SendMessage(chatId, "Нажмите 'Поделиться номером телефона' что бы записать ваши контактные данные!").replyMarkup(keyboard);
            case GET_ANIMAL:

                if (choice.isPresent()) {
                    if (Objects.equals(choice.get().getShelterType(), CallbackRequest.DOGS.getCode())) {
                        service.showButtonsForHowTakeDog(inlineKeyboard);
                        return new SendMessage(chatId, "Как взять собаку из приюта?").replyMarkup(inlineKeyboard);
                    } else {
                        service.showButtonsForHowTakeCat(inlineKeyboard);
                    }
                    return new SendMessage(chatId, "Как взять кошку из приюта?").replyMarkup(inlineKeyboard);
                }
            case REPORT_ANIMAL:
                return new SendMessage(chatId, "Присылаю отчет о питомце!");

            case INTRODUCTION_RULES:
                if (animalAdvice.isPresent()) {
                    return new SendMessage(chatId, animalAdvice.get().getIntroductionRules());
                }
            case NECESSARY_DOCUMENTS:
                if (animalAdvice.isPresent()) {
                    return new SendMessage(chatId, animalAdvice.get().getNecessaryDocuments());
                }
            case TRANSPORTATION_RECOMMENDATIONS:
                if (animalAdvice.isPresent()) {
                    return new SendMessage(chatId, animalAdvice.get().getTransportationRecommendations());
                }
            case PUPPY_HOME_ARRANGEMENT:
                if (animalAdvice.isPresent()) {
                    return new SendMessage(chatId, animalAdvice.get().getPuppyHomeArrangement());
                }
            case ADULT_HOME_ARRANGEMENT:
                if (animalAdvice.isPresent()) {
                    return new SendMessage(chatId, animalAdvice.get().getAdultHomeArrangement());
                }
            case HOME_ARRANGEMENT_WITH_LIMITED_ABILITY:
                if (animalAdvice.isPresent()) {
                    return new SendMessage(chatId, animalAdvice.get().getHomeArrangementWithLimitedAbility());
                }
            case INITIAL_COMMUNICATION_WITH_DOG_ADVICE:
                if (animalAdvice.isPresent()) {
                    return new SendMessage(chatId, animalAdvice.get().getInitialCommunicationWithDogAdvice());
                }
            case BEST_DOG_HANDLERS:
                if (animalAdvice.isPresent()) {
                    return new SendMessage(chatId, animalAdvice.get().getBestDogHandlers());
                }
            case REASONS_FOR_REFUSING_ANIMAL_TRANSFER:
                if (animalAdvice.isPresent()) {
                    return new SendMessage(chatId, animalAdvice.get().getReasonsForRefusingAnimalTransfer());
                }
            case KITTEN_HOME_ARRANGEMENT:
                if (animalAdvice.isPresent()) {
                    return new SendMessage(chatId, animalAdvice.get().getKittenHomeArrangement());
                }
            case STEP_BACK_INFO_SHELTER:
                service.showButtonsForDogsCats(inlineKeyboard);

                if (choice.isPresent()) {
                    if (Objects.equals(choice.get().getShelterType(), CallbackRequest.DOGS.getCode())) {
                        return new SendMessage(chatId, "Выбран приют для собак, чем я могу тебе помочь?\uD83D\uDC15").replyMarkup(inlineKeyboard);
                    } else {
                        return new SendMessage(chatId, "Выбран приют для кошек, чем я могу тебе помочь?\uD83D\uDC08\u200D⬛").replyMarkup(inlineKeyboard);
                    }
                }
            case STEP_BACK_CHOOSING_SHELTER:
                service.addButton(inlineKeyboard, CallbackRequest.CATS.getName(), CallbackRequest.CATS);
                service.addButton(inlineKeyboard, CallbackRequest.DOGS.getName(), CallbackRequest.DOGS);
                return new SendMessage(chatId, " Какой приют вы ищите?").replyMarkup(inlineKeyboard);
            case HELP:
            default:
                return new SendMessage(chatId, "Вызываю волонтера!");
        }
    }

    private CallbackRequest getCallbackRequest(String data) {
        CallbackRequest callbackRequest = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            //десериализуем данные из события бота в объект
            callbackRequest = objectMapper.readValue(data, CallbackRequest.class);
        } catch (JsonProcessingException e) {
            logger.error(String.valueOf(e));
        }
        return callbackRequest;
    }
}
