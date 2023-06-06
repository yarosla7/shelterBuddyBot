package com.devpro.shelterBuddyBot.service.impl;

import com.devpro.shelterBuddyBot.model.Animal;
import com.devpro.shelterBuddyBot.model.Reports;
import com.devpro.shelterBuddyBot.model.ShelterClients;
import com.devpro.shelterBuddyBot.model.Volunteer;
import com.devpro.shelterBuddyBot.repository.dao.AnimalDao;
import com.devpro.shelterBuddyBot.repository.dao.ReportsDao;
import com.devpro.shelterBuddyBot.repository.dao.ShelterClientsDao;
import com.devpro.shelterBuddyBot.repository.dao.VolunteerDao;
import com.devpro.shelterBuddyBot.service.AdminService;
import com.devpro.shelterBuddyBot.service.ShelterService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AnimalDao animalDao;
    private final ShelterClientsDao shelterClientsDao;
    private final ReportsDao reportsDao;
    private final VolunteerDao volunteerDao;
    private final TelegramBot telegramBot;
    private final ShelterService service;

    @Override
    public void adoptAnimal(Integer animalId, Integer userId) {
        Optional<Animal> animalOpt = animalDao.findById(animalId);
        Optional<ShelterClients> clientOpt = shelterClientsDao.findById(userId);

        if (animalOpt.isPresent() && clientOpt.isPresent() && !clientOpt.get().getTookAnimal()) {
            animalOpt.get().setInShelter(false);
            animalOpt.get().setUserId(clientOpt.get().getUserId());
            animalOpt.get().setAdoptDate(LocalDate.now().plusDays(30));
            clientOpt.get().setTookAnimal(true);
            clientOpt.get().setAnimal(animalOpt.get());

            shelterClientsDao.save(clientOpt.get());

            Integer chatId = clientOpt.get().getChatId();
            String animalType = animalOpt.get().getTypeOfAnimal().equals("DOG") ? "собаку" : "кота";
            String petName = animalOpt.get().getPetName();
            String breed = animalOpt.get().getBreed();

            telegramBot.execute(new SendMessage(chatId, "✅Вы усыновили " + animalType + ": " +
                    "\n" + petName + " " + breed +
                    "\n\nУ вас начался испытательный период в 30 дней, вам нужно каждый день отправлять отчет о вашем животном в бота. Желаем удачи!"
            ));
        }
    }

    @Override
    public List<Reports> showAllReports() {
        return reportsDao.findAllByIsReportOkIsNull();
    }

    @Override
    public String countReport() {
        List<Reports> reportsList = showAllReports();
        int count = reportsList.size();
        return String.valueOf(count);
    }

    @Override
    public String countApplicantAnimals() {
        return String.valueOf(animalDao.findAllByAdoptedIsFalseAndAdoptDate(LocalDate.now()).size());
    }


    @Override
    public Reports showReports(Message message) {
        List<Reports> reports = showAllReports();
        Reports reportReturn;
        for (Reports report : reports) {
            if (Objects.isNull(report.getIsReportOk())) {
                reportReturn = report;
                return reportReturn;
            }
        }
        return null;
    }

    @Override
    public void addVolunteer(Long chatId, String name) {
        try {
            volunteerDao.save(Volunteer.
                    builder().
                    chatId(chatId).
                    fullName(name).
                    build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 55 20 * * ?")
    @Transactional
    public void checkLastReports() {
        List<Reports> lastReport = reportsDao.findLastReport();
        lastReport.forEach(report -> {
            LocalDate localDate = LocalDate.now().minusDays(2);

            if (localDate.compareTo(report.getDate()) >= 1) {
                reportsDao.findById(report.getId()).ifPresent(reports -> {
                    String userName = service.getUserName(reports.getShelterClients().getName());
                    LocalDate date = report.getDate();
                    Optional<Volunteer> volunteerOpt = volunteerDao.findFirstByChatIdNotNull();
                    volunteerOpt.ifPresent(volunteer -> {
                        Integer userChatId = reports.getShelterClients().getChatId();
                        Long chatId = volunteer.getChatId();
                        String name = volunteer.getFullName();
                        String number = reports.getShelterClients().getNumber();


                        telegramBot.execute(new SendMessage(userChatId, "❗️Здраствуйте" + userName + "!" + "\nС вами свяжется волонтер, чтобы уточнить почему вы не присылаете отчет последние три дня"));
                        telegramBot.execute(new SendMessage(chatId, "❗️Здраствуйте, " + name + "!" +
                                "\nУсыновитель" + userName + " не отправлял отчет последние 3 дня, последний отчет был " +
                                date + "\nСвяжитесь с ним, чтобы уточнить причину, номер телефона усыновителя: " + number));
                    });
                });
            } else if (!localDate.isBefore(report.getDate())) {
                reportsDao.findById(report.getId()).ifPresent(reports -> {
                    Integer chatId = reports.getShelterClients().getChatId();
                    String userName = service.getUserName(reports.getShelterClients().getName());
                    LocalDate date = report.getDate();
                    telegramBot.execute(new SendMessage(chatId, "❗️Здраствуйте" + userName + "!" +
                            "\nВаш последний отчет был " +
                            date +
                            "\nПришлите пожалуйста отчет, либо с вами свяжется волонтер что бы уточнить причину, почему вы не присылаете отчет!"));
                });
            }
        });
    }

    @Override
    public Animal showApplicantsAnimals(Message message) {
        List<Animal> animals = animalDao.findAllByAdoptedIsFalseAndAdoptDate(LocalDate.now());
        Animal animalReturn;
        for (Animal animal : animals) {
            if (!animal.getAdopted()) {
                animalReturn = animal;
                return animalReturn;
            }
        }
        return null;
    }
}
