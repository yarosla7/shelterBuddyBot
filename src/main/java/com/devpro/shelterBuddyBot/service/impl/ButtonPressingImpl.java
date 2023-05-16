package com.devpro.shelterBuddyBot.service.impl;

import com.devpro.shelterBuddyBot.bot.listner.TelegramBotUpdatesListener;
import com.devpro.shelterBuddyBot.model.AnimalAdvice;
import com.devpro.shelterBuddyBot.model.Choice;
import com.devpro.shelterBuddyBot.model.ShelterBuddy;
import com.devpro.shelterBuddyBot.model.entity.Animal;
import com.devpro.shelterBuddyBot.model.entity.Reports;
import com.devpro.shelterBuddyBot.model.entity.ShelterClients;
import com.devpro.shelterBuddyBot.model.entity.Volunteer;
import com.devpro.shelterBuddyBot.repository.dao.*;
import com.devpro.shelterBuddyBot.service.AdminService;
import com.devpro.shelterBuddyBot.service.ButtonPressing;
import com.devpro.shelterBuddyBot.service.ShelterService;
import com.devpro.shelterBuddyBot.util.CallbackRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ButtonPressingImpl implements ButtonPressing {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final ShelterService service;
    private final ChoiceDao choiceDao;
    private final AnimalAdviceDao animalAdviceDao;
    private final ShelterClientsDao shelterClientsDao;
    private final AdminService adminService;
    private final ReportsDao reportsDao;
    private final TelegramBot telegramBot;
    private final AnimalDao animalDao;
    private final VolunteerDao volunteerDao;

    @Transactional
    @Override
    public SendMessage handleCallback(CallbackQuery callbackQuery) {
        Message message = callbackQuery.message();
        long chatId = message.chat().id();
        Optional<Reports> report = Optional.ofNullable(adminService.showReports(message));
        Optional<Animal> animal = Optional.ofNullable(adminService.showApplicantsAnimals(message));
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

                service.showButtonsForDogsCats(inlineKeyboard);

                if (callbackRequest == CallbackRequest.DOGS) {
                    return new SendMessage(chatId, "\uD83D\uDC15Выбран приют для собак, чем я могу тебе помочь?").replyMarkup(inlineKeyboard);
                } else {
                    return new SendMessage(chatId, "\uD83D\uDC08\u200D⬛Выбран приют для кошек, чем я могу тебе помочь?").replyMarkup(inlineKeyboard);
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
                shelterBuddy = service.getShelterBuddy(chatId);
                if (shelterBuddy.isPresent()) {
                    Optional<ShelterClients> user = shelterClientsDao
                            .findByChatIdAndTookAnimalAndShelterBuddy((int) chatId, true, shelterBuddy.get());
                    if (user.isEmpty()) {
                        return new SendMessage(chatId, "❌У вас нет животного из этого приюта!");
                    } else {
                        return new SendMessage(chatId, """                          
                                Пришлите отчет в следующей форме !
                                - Фото животного, далее пишите в описании фотографии:
                                1 Рацион животного.
                                2 Общее самочувствие и привыкание к новому месту.
                                3 Изменение в поведении: отказ от старых привычек, приобретение новых.""");
                    }
                }
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
                        return new SendMessage(chatId, "\uD83D\uDC15Выбран приют для собак, чем я могу тебе помочь?").replyMarkup(inlineKeyboard);
                    } else {
                        return new SendMessage(chatId, "\uD83D\uDC08\u200D⬛Выбран приют для кошек, чем я могу тебе помочь?").replyMarkup(inlineKeyboard);
                    }
                }
            case STEP_BACK_CHOOSING_SHELTER:
                service.addButton(inlineKeyboard, CallbackRequest.CATS.getName(), CallbackRequest.CATS);
                service.addButton(inlineKeyboard, CallbackRequest.DOGS.getName(), CallbackRequest.DOGS);
                return new SendMessage(chatId, " Какой приют вы ищите?").replyMarkup(inlineKeyboard);
            case SHOW_REPORTS:
                if (report.isPresent()) {
                    Optional<Animal> animalById = animalDao.findById(report.get().getAnimal().getAnimalId());
                    if (animalById.isPresent()) {
                        String animalType = animalById.get().getTypeOfAnimal().equals("DOG") ? "собака" : "кошка";
                        String breed = animalById.get().getBreed();
                        String petName = animalById.get().getPetName();
                        String reportText = report.get().getReportText();
                        String photoLink = report.get().getTelegramPhotoLink();
                        SendPhoto sendPhoto = new SendPhoto(chatId, photoLink);
                        telegramBot.execute(new SendMessage(chatId, "Отчет от усыновителя: " + report.get().getShelterClients().getName() +
                                "\n" + animalType + " " + petName + " " + breed));
                        telegramBot.execute(sendPhoto);
                        service.addButton(inlineKeyboard, CallbackRequest.REPORT_OK.getName(), CallbackRequest.REPORT_OK);
                        service.addButton(inlineKeyboard, CallbackRequest.REPORT_NOT_OK.getName(), CallbackRequest.REPORT_NOT_OK);
                        return new SendMessage(chatId, reportText).replyMarkup(inlineKeyboard);
                    }
                } else {
                    return new SendMessage(chatId, "✅Отчетов больше нет").replyMarkup(inlineKeyboard);
                }
            case REPORT_OK:
                if (report.isPresent()) {
                    Optional<Reports> reports = reportsDao.findById(report.get().getId());
                    if (reports.isPresent()) {
                        reports.get().setIsReportOk(true);
                        reportsDao.save(reports.get());
                        service.addButton(inlineKeyboard, CallbackRequest.SHOW_REPORTS.getName() + ": " + adminService.countReport(), CallbackRequest.SHOW_REPORTS);
                        service.addButton(inlineKeyboard, CallbackRequest.SHOW_ANIMALS.getName() + ": " + adminService.countApplicantAnimals(), CallbackRequest.SHOW_ANIMALS);
                        telegramBot.execute(new SendMessage(chatId, "✅Отчет потдвержден! \n"));
                        telegramBot.execute(new SendMessage(reports.get().getShelterClients().getChatId(), CallbackRequest.MESSAGE_REPORT_OK.getName()));
                        return new SendMessage(chatId, "Колличество отчетов для просмотра: " + adminService.countReport()).replyMarkup(inlineKeyboard);
                    }
                }
                return new SendMessage(chatId, "Произошла ошибка потверждения отчета, сообщите вашему программисту что тут проблема!").replyMarkup(inlineKeyboard);
            case REPORT_NOT_OK:
                if (report.isPresent()) {
                    Optional<Reports> reports = reportsDao.findById(report.get().getId());
                    if (reports.isPresent()) {
                        reports.get().setIsReportOk(false);
                        reportsDao.save(reports.get());
                        service.addButton(inlineKeyboard, CallbackRequest.SHOW_REPORTS.getName() + ": " + adminService.countReport(), CallbackRequest.SHOW_REPORTS);
                        service.addButton(inlineKeyboard, CallbackRequest.SHOW_ANIMALS.getName() + ": " + adminService.countApplicantAnimals(), CallbackRequest.SHOW_ANIMALS);
                        telegramBot.execute(new SendMessage(chatId, "❌Отчет отклонен! \n"));
                        telegramBot.execute(new SendMessage(reports.get().getShelterClients().getChatId(), CallbackRequest.MESSAGE_REPORT_NOT_OK.getName()));
                        return new SendMessage(chatId, "Колличество отчетов для просмотра: " + adminService.countReport()).replyMarkup(inlineKeyboard);
                    }
                }
                return new SendMessage(chatId, "Произошла ошибка отклонения отчета, сообщите вашему программисту что тут проблема!")
                        .replyMarkup(inlineKeyboard);
            case SHOW_ANIMALS:
                if (animal.isPresent()) {
                    Optional<ShelterClients> user = shelterClientsDao.findById(animal.get().getUserId());
                    if (user.isPresent()) {
                        String userName = " " + user.get().getName();
                        if (Objects.isNull(user.get().getName())) {
                            userName = "";
                        }
                        String number = user.get().getNumber();
                        String petName = animal.get().getPetName();
                        String breed = animal.get().getBreed();
                        String animalType = animal.get().getTypeOfAnimal().equals("DOG") ? "собака" : "кошка";

                        service.addButton(inlineKeyboard, CallbackRequest.ANIMAL_ADOPTED.getName(), CallbackRequest.ANIMAL_ADOPTED);
                        service.addButton(inlineKeyboard, CallbackRequest.ANIMAL_EXTEND_PERIOD_14.getName(), CallbackRequest.ANIMAL_EXTEND_PERIOD_14);
                        service.addButton(inlineKeyboard, CallbackRequest.ANIMAL_EXTEND_PERIOD_30.getName(), CallbackRequest.ANIMAL_EXTEND_PERIOD_30);
                        service.addButton(inlineKeyboard, CallbackRequest.ANIMAL_NOT_ADOPTED.getName(), CallbackRequest.ANIMAL_NOT_ADOPTED);
                        return new SendMessage(chatId, "Усыновитель " + userName + ", телефон: " + number + "\nПрошел испытательный период:\n" +
                                "Усыновленная " + animalType + ": " + petName + ", " + breed).replyMarkup(inlineKeyboard);
                    } else {
                        return new SendMessage(chatId, "Произошла ошибка , сообщите вашему программисту что тут проблема!")
                                .replyMarkup(inlineKeyboard);
                    }
                } else {
                    return new SendMessage(chatId, "✅Претендентов на усыновление больше нет").replyMarkup(inlineKeyboard);
                }
            case ANIMAL_ADOPTED:
                if (animal.isPresent()) {
                    Optional<ShelterClients> user = shelterClientsDao.findById(animal.get().getUserId());
                    if (user.isPresent()) {
                        user.get().setTookAnimal(false);
                        user.get().setAnimal(null);
                        animal.get().setAdopted(true);
                        String userName = service.getUserName(user.get().getName());
                        service.addButton(inlineKeyboard, CallbackRequest.SHOW_REPORTS.getName() + ": " + adminService.countReport(), CallbackRequest.SHOW_REPORTS);
                        service.addButton(inlineKeyboard, CallbackRequest.SHOW_ANIMALS.getName() + ": " + adminService.countApplicantAnimals(), CallbackRequest.SHOW_ANIMALS);
                        Integer userChatId = user.get().getChatId();
                        telegramBot.execute(new SendMessage(chatId, "✅Вы подтвердили усыновление, клиенту " + userName));
                        telegramBot.execute(new SendMessage(userChatId, "✅Вы успешно прошли испытательный период! Удачи!"));
                        return new SendMessage(chatId, "\uD83E\uDDF0Вы находитесь в административном меню").replyMarkup(inlineKeyboard);
                    }
                    return new SendMessage(chatId, "Произошла ошибка, клиент не найдет в бд!")
                            .replyMarkup(inlineKeyboard);
                }
                return new SendMessage(chatId, "Произошла ошибка, животное не найдено в бд")
                        .replyMarkup(inlineKeyboard);
            case ANIMAL_NOT_ADOPTED:
                if (animal.isPresent()) {
                    Optional<ShelterClients> user = shelterClientsDao.findById(animal.get().getUserId());
                    if (user.isPresent()) {
                        user.get().setAnimal(null);
                        user.get().setTookAnimal(false);
                        animal.get().setUserId(null);
                        animal.get().setInShelter(true);
                        animal.get().setAdoptDate(null);
                        Integer userChatId = user.get().getChatId();
                        String userName = service.getUserName(user.get().getName());

                        service.addButton(inlineKeyboard, CallbackRequest.SHOW_REPORTS.getName() + ": " + adminService.countReport(), CallbackRequest.SHOW_REPORTS);
                        service.addButton(inlineKeyboard, CallbackRequest.SHOW_ANIMALS.getName() + ": " + adminService.countApplicantAnimals(), CallbackRequest.SHOW_ANIMALS);
                        telegramBot.execute(new SendMessage(chatId, "❌Вы отказали в усыновлении животного усыновителю " + userName));
                        telegramBot.execute(new SendMessage(userChatId, "❌Вы не прошли испытательный период, волонтер свяжется с вами что бы забрать животное в приют!"));
                        return new SendMessage(chatId, "\uD83E\uDDF0Вы находитесь в административном меню").replyMarkup(inlineKeyboard);
                    }
                    return new SendMessage(chatId, "Произошла ошибка, клиент не найдет в бд!")
                            .replyMarkup(inlineKeyboard);
                }
                return new SendMessage(chatId, "Произошла ошибка, животное не найдено в бд")
                        .replyMarkup(inlineKeyboard);
            case ANIMAL_EXTEND_PERIOD_14:
                if (animal.isPresent()) {
                    Optional<ShelterClients> user = shelterClientsDao.findById(animal.get().getUserId());
                    LocalDate localDate = animal.get().getAdoptDate().plusDays(14);
                    animal.get().setAdoptDate(localDate);
                    if (user.isPresent()) {
                        Integer userChatId = user.get().getChatId();
                        String userName = service.getUserName(user.get().getName());
                        telegramBot.execute(new SendMessage(userChatId, "Ваш испытательный срок продлен на 14 дней, продолжайте присылать отчеты о животном в этот период."));
                        telegramBot.execute(new SendMessage(chatId, "Вы продилили испытательный срок на 14 дней усыновителю " + userName));
                        service.addButton(inlineKeyboard, CallbackRequest.SHOW_REPORTS.getName() + ": " + adminService.countReport(), CallbackRequest.SHOW_REPORTS);
                        service.addButton(inlineKeyboard, CallbackRequest.SHOW_ANIMALS.getName() + ": " + adminService.countApplicantAnimals(), CallbackRequest.SHOW_ANIMALS);

                        return new SendMessage(chatId, "\uD83E\uDDF0Вы находитесь в административном меню").replyMarkup(inlineKeyboard);
                    }
                    return new SendMessage(chatId, "Произошла ошибка, клиент не найдет в бд!")
                            .replyMarkup(inlineKeyboard);
                }
                return new SendMessage(chatId, "Произошла ошибка, животное не найдено в бд")
                        .replyMarkup(inlineKeyboard);

            case ANIMAL_EXTEND_PERIOD_30:
                if (animal.isPresent()) {
                    Optional<ShelterClients> user = shelterClientsDao.findById(animal.get().getUserId());
                    LocalDate localDate = animal.get().getAdoptDate().plusDays(30);
                    animal.get().setAdoptDate(localDate);
                    if (user.isPresent()) {
                        Integer userChatId = user.get().getChatId();
                        String userName = service.getUserName(user.get().getName());
                        telegramBot.execute(new SendMessage(userChatId, "Ваш испытательный срок продлен на 30 дней, продолжайте присылать отчеты о животном в этот период."));
                        telegramBot.execute(new SendMessage(chatId, "Вы продилили испытательный срок на 30 дней усыновителю " + userName));
                        service.addButton(inlineKeyboard, CallbackRequest.SHOW_REPORTS.getName() + ": " + adminService.countReport(), CallbackRequest.SHOW_REPORTS);
                        service.addButton(inlineKeyboard, CallbackRequest.SHOW_ANIMALS.getName() + ": " + adminService.countApplicantAnimals(), CallbackRequest.SHOW_ANIMALS);
                        return new SendMessage(chatId, "\uD83E\uDDF0Вы находитесь в административном меню").replyMarkup(inlineKeyboard);
                    }
                    return new SendMessage(chatId, "Произошла ошибка, клиент не найдет в бд!")
                            .replyMarkup(inlineKeyboard);
                }
                return new SendMessage(chatId, "Произошла ошибка, животное не найдено в бд")
                        .replyMarkup(inlineKeyboard);
            case HELP:
                Keyboard keyboard1 = new ReplyKeyboardMarkup(new KeyboardButton("Поделиться номером телефона").requestContact(true));
                if (service.checkClientChatIdInShelter(message)) {
                    telegramBot.execute(new SendMessage(chatId, "Для вызова волонтера из этого приюта, вам нужно поделиться номером телефона"));
                    return new SendMessage(chatId, "Нажмите 'Поделиться номером телефона' что бы записать ваши контактные данные!").replyMarkup(keyboard1);
                }
                Optional<Volunteer> volunteerOpt = volunteerDao.findFirstByChatIdNotNull();
                volunteerOpt.ifPresent(volunteer -> {
                    Long chatIdVolunteer = volunteer.getChatId();
                    String name = volunteer.getFullName();

                    Optional<ShelterBuddy> shelterBuddy1 = service.getShelterBuddy(chatId);
                    if (shelterBuddy1.isPresent()) {
                        Optional<ShelterClients> shelterClient = shelterClientsDao.findByChatIdAndShelterBuddy((int) chatId, shelterBuddy1.get());
                        if (shelterClient.isPresent()) {
                            String userName = " " + shelterClient.get().getName();
                            if (Objects.isNull(shelterClient.get().getName())) {
                                userName = " Anon";
                            }
                            String number = shelterClient.get().getNumber();
                            telegramBot.execute(new SendMessage(chatIdVolunteer, "\uD83D\uDCACЗдраствуйте " + name + ", вас просит помочь" + userName +
                                    "\n" + "номер телефона: " + number +
                                    "\nпожалуйтса, свяжитесь с пользователем что бы уточнить, в чем проблема?"
                            ));
                        }
                    }
                });
                return new SendMessage(chatId, "\uD83D\uDCACС вами скоро свяжется волонтер!");
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
