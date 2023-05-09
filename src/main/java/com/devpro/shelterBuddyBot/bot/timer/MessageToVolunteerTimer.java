package com.devpro.shelterBuddyBot.bot.timer;

import com.devpro.shelterBuddyBot.bot.listner.TelegramBotUpdatesListener;
import com.devpro.shelterBuddyBot.model.*;
import com.devpro.shelterBuddyBot.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class MessageToVolunteerTimer {
    private final MessageToVolunteerService messageToVolunteerService;
    private final ShelterService shelterService;
    private final ButtonPressing buttonPressing;
    private final CatOwnerService catOwnerService;
    private final TrialPeriodService trialPeriodService;
    private final TelegramBotUpdatesListener telegramBotUpdatesListener;
    private final CatShelterService catShelterService;
    private final CatService catService;

}
