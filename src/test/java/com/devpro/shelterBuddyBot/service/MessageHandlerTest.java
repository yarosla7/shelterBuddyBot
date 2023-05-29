package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.model.*;
import com.devpro.shelterBuddyBot.repository.dao.ReportsDao;
import com.devpro.shelterBuddyBot.repository.dao.ShelterClientsDao;
import com.devpro.shelterBuddyBot.repository.dao.VolunteerDao;
import com.devpro.shelterBuddyBot.service.impl.MessageHandlerImpl;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;


import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageHandlerTest {
    private ShelterClientsDao shelterClientsDao;
    private ShelterService service;
    private ReportsDao reportsDao;
    private VolunteerDao volunteerDao;
    private AdminService adminService;
    private MessageHandlerImpl messageHandler;

    @BeforeEach
    void setUp() {
        // Mock the dependencies
        shelterClientsDao = mock(ShelterClientsDao.class);
        service = mock(ShelterService.class);
        reportsDao = mock(ReportsDao.class);
        volunteerDao = mock(VolunteerDao.class);
        adminService = mock(AdminService.class);

        // Create an instance of the class under test
        messageHandler = new MessageHandlerImpl(shelterClientsDao, service, reportsDao, volunteerDao, adminService);
    }

    @Test
    void testContactProcessing() {
        long chatId = 123;
        Message message = mock(Message.class);
        Contact contact = mock(Contact.class);
        ShelterBuddy shelterBuddy = mock(ShelterBuddy.class);
        when(message.chat()).thenReturn(mock(Chat.class));
        when(message.chat().id()).thenReturn(chatId);
        when(message.contact()).thenReturn(contact);
        when(contact.firstName()).thenReturn("John");
        when(contact.phoneNumber()).thenReturn("1234567890");
        when(service.getShelterBuddy(chatId)).thenReturn(Optional.of(shelterBuddy));
        when(service.checkClientNumber(message)).thenReturn(true);

        messageHandler.contactProcessing(message);

        verify(shelterClientsDao, times(1)).save(any(ShelterClients.class));
        verify(service, times(1)).getShelterBuddy(chatId);
        verify(service, times(1)).checkClientNumber(message);
    }


}
