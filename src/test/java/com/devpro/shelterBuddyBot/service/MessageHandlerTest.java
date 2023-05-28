package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.model.Volunteer;
import com.devpro.shelterBuddyBot.repository.dao.ReportsDao;
import com.devpro.shelterBuddyBot.repository.dao.ShelterClientsDao;
import com.devpro.shelterBuddyBot.repository.dao.VolunteerDao;
import com.devpro.shelterBuddyBot.service.impl.MessageHandlerImpl;
import com.devpro.shelterBuddyBot.service.impl.ShelterServiceImpl;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageHandlerTest {
    @Mock
    VolunteerDao volunteerDao;
    @Mock
    Message message;
    @Mock
    Chat chat;
    private Volunteer volunteer;
    @Mock
    ShelterClientsDao shelterClientsDao;
    @Mock
    ShelterServiceImpl shelterService;
    @Mock
    ReportsDao reportsDao;
    @Mock
    AdminService adminService;
    @InjectMocks
    MessageHandlerImpl messageHandler;
    @Mock
    SendMessage sendMessage;

}
