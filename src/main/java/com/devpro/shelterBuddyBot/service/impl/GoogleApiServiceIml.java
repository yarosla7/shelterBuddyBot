package com.devpro.shelterBuddyBot.service.impl;

import com.devpro.shelterBuddyBot.model.ShelterBuddy;
import com.devpro.shelterBuddyBot.model.entity.Animal;
import com.devpro.shelterBuddyBot.model.entity.ClientDataReport;
import com.devpro.shelterBuddyBot.model.entity.Reports;
import com.devpro.shelterBuddyBot.model.entity.ShelterClients;
import com.devpro.shelterBuddyBot.repository.dao.ClientDataReportDao;
import com.devpro.shelterBuddyBot.repository.dao.ReportsDao;
import com.devpro.shelterBuddyBot.repository.dao.ShelterClientsDao;
import com.devpro.shelterBuddyBot.service.GoogleApiService;
import com.devpro.shelterBuddyBot.util.GoogleApiUtil;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleApiServiceIml implements GoogleApiService {
    private final GoogleApiUtil googleApiUtil;
    private final ClientDataReportDao clientDataReportDao;
    private final ReportsDao reportsDao;
    private final TelegramBot telegramBot;
    private final ShelterClientsDao shelterClientsDao;


    @Override
    public List<ClientDataReport> readDataFromGoogleSheet() throws GeneralSecurityException, IOException {
        return googleApiUtil.getDataFromSheet();
    }

    @Override
    public void saveToDataBase() throws GeneralSecurityException, IOException {
        List<ClientDataReport> listClientDataReports = googleApiUtil.getDataFromSheet();
        for (ClientDataReport clientDataReport : listClientDataReports) {
            Optional<ClientDataReport> clientDataReportOpt = clientDataReportDao.findByIdReport(clientDataReport.getIdReport());
            if (clientDataReportOpt.isEmpty()) {
                clientDataReportDao.save(clientDataReport);
            }
        }
    }

    @Scheduled(cron = "20 * * * * ?")
    private void dataBaseAndReportProcess() throws GeneralSecurityException, IOException {
        saveToDataBase();

        List<ClientDataReport> googleReports = clientDataReportDao.findByProcessedFalseOrProcessedNull();

        for (ClientDataReport googleReport : googleReports) {
            Integer clientId = googleReport.getClientId();
            Optional<ShelterClients> client = shelterClientsDao.findById(clientId);
            if (client.isPresent()) {
                String rationGgl = googleReport.getRation();
                String wellBeingGgl = googleReport.getWellBeing();
                String behaviorChangesGgl = googleReport.getBehaviorChanges();
                String photoLinkGgl = googleReport.getPhotoLink();
                Integer chatId = client.get().getChatId();
                ShelterBuddy shelterBuddy = client.get().getShelterBuddy();
                Animal animal = client.get().getAnimal();
                String reportText = "\uD83D\uDD38Рацион животного: " +
                        "\n \uD83D\uDC64" + rationGgl +
                        "\n\uD83D\uDD38Общее самочувствие и привыкание к новому месту: " +
                        "\n \uD83D\uDC64" + wellBeingGgl +
                        "\n\uD83D\uDD38Изменение в поведении: отказ от старых привычек, приобретение новых: " +
                        "\n \uD83D\uDC64" + behaviorChangesGgl;

                reportsDao.save(Reports.builder().
                        reportText(reportText).
                        animal(animal).
                        shelterBuddy(shelterBuddy).
                        telegramPhotoLink(photoLinkGgl).shelterClients(client.get())
                        .build());
                googleReport.setProcessed(true);
                clientDataReportDao.save(googleReport);

                telegramBot.execute(new SendMessage(chatId, "✅Ваш отчет сохранен!\nКак только отчет будет проверен, мы пришлем вам ответ."));
            }
        }
    }
}
