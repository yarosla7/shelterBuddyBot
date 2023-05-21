package com.devpro.shelterBuddyBot.service.impl;

import com.devpro.shelterBuddyBot.model.Reports;
import com.devpro.shelterBuddyBot.model.ShelterBuddy;
import com.devpro.shelterBuddyBot.model.ShelterClients;
import com.devpro.shelterBuddyBot.model.Volunteer;
import com.devpro.shelterBuddyBot.repository.dao.ReportsDao;
import com.devpro.shelterBuddyBot.repository.dao.ShelterClientsDao;
import com.devpro.shelterBuddyBot.repository.dao.VolunteerDao;
import com.devpro.shelterBuddyBot.service.AdminService;
import com.devpro.shelterBuddyBot.service.MessageHandler;
import com.devpro.shelterBuddyBot.service.ShelterService;
import com.devpro.shelterBuddyBot.util.CallbackRequest;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageHandlerImpl implements MessageHandler {

    private final ShelterClientsDao shelterClientsDao;
    private final ShelterService service;
    private final ReportsDao reportsDao;
    private final VolunteerDao volunteerDao;
    private final AdminService adminService;

    @Nullable
    public SendMessage contactProcessing(Message message) {
        long chatId = message.chat().id();
        //обрабатываем отправку контакта и удаляем кнопки ReplyKeyboardRemove
        try {
            // метод репозитория сохранения
            Optional<ShelterBuddy> shelterBuddy = service.getShelterBuddy(chatId);

            if (service.checkClientNumber(message)) {
                shelterBuddy.ifPresent(buddy -> shelterClientsDao.save(ShelterClients.builder()
                        .tookAnimal(false)
                        .name(message.contact().firstName())
                        .chatId((int) chatId)
                        .number(message.contact().phoneNumber())
                        .shelterBuddy(buddy)
                        .build()));
            } else {
                return new SendMessage(chatId, "Ваш номер телефона уже есть в базе данных этого приюта!").replyMarkup(new ReplyKeyboardRemove());
            }
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
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();

        if ("/start".equals(message.text())) {

            service.addButton(inlineKeyboard, CallbackRequest.CATS.getName(), CallbackRequest.CATS);
            service.addButton(inlineKeyboard, CallbackRequest.DOGS.getName(), CallbackRequest.DOGS);

            String userName = service.getUserName(message.chat().firstName());
            //Тут берется имя прямо с чата, если будет общий чат будет выводиться имя чата поидеи.
            return new SendMessage(chatId, "Привет" + userName + "!" +
                    "\nЯ расскажу тебе о приютах нашего города и помогу тебе найти или пристроить потеряшку!" +
                    "\n\nКакой приют вы ищите?").replyMarkup(inlineKeyboard);

        } else if ("/admin".equals(message.text()) && checkVolunteer(message)) {
            String name = volunteerDao.findByChatId(chatId).getFullName();
            service.addButton(inlineKeyboard, CallbackRequest.SHOW_REPORTS.getName() + ": " + adminService.countReport(), CallbackRequest.SHOW_REPORTS);
            service.addButton(inlineKeyboard, CallbackRequest.SHOW_ANIMALS.getName() + ": " + adminService.countApplicantAnimals(), CallbackRequest.SHOW_ANIMALS);

            return new SendMessage(chatId, "\uD83E\uDDF0Привет, " + name + "," + " вы находитесь в административном меню\n").replyMarkup(inlineKeyboard);

        } else {
            return new SendMessage(chatId, """
                    Бот не знает такой команды. Введите /start для начала работы с ботом""");
        }
    }

    @Override
    public SendMessage reportProcessing(Message message) {
        long chatId = message.chat().id();
        Optional<ShelterBuddy> shelterBuddy = service.getShelterBuddy(chatId);

        if (shelterBuddy.isPresent()) {
            Optional<ShelterClients> user = shelterClientsDao
                    .findByChatIdAndTookAnimalAndShelterBuddy((int) chatId, true, shelterBuddy.get());
            if (user.isEmpty()) {
                return new SendMessage(chatId, "\uD83D\uDEABУ вас нет животного из этого приюта!");
            }
        }


        if (Objects.isNull(message.photo())) {
            return new SendMessage(chatId, "Пожалуйста, добавьте фото!");
        } else if (Objects.isNull(message.caption())) {
            return new SendMessage(chatId, """
                    Пришлите отчет в следующей форме !
                    - Фото животного, далее пишите в описании фотографии:
                    1 Рацион животного.
                    2 Общее самочувствие и привыкание к новому месту.
                    3 Изменение в поведении: отказ от старых привычек, приобретение новых.""");
        }

        if (shelterBuddy.isPresent()) {
            Optional<ShelterClients> user = shelterClientsDao.findByChatIdAndTookAnimalAndShelterBuddy((int) chatId, true, shelterBuddy.get());
            if (user.isPresent()) {
                reportsDao.save(Reports.builder()
                        .reportText(message.caption())
                        .shelterClients(user.get())
                        .animal(user.get().getAnimal())
                        .shelterBuddy(shelterBuddy.get())
                        .telegramPhotoLink(message.photo()[message.photo().length - 1].fileId())
                        .build());
                return new SendMessage(chatId, "✅Ваш отчет сохранен!\nКак только отчет будет проверен, мы пришлем вам ответ.");
            }
        }
        return new SendMessage(chatId, "Не удалось записать отчет!");
    }

    private Boolean checkVolunteer(Message message) {
        boolean checkVolunteer = false;
        long chatId = message.chat().id();
        Volunteer volunteer = volunteerDao.findByChatId(chatId);

        if (Objects.nonNull(volunteer)) {
            checkVolunteer = true;
        }
        return checkVolunteer;
    }
}
