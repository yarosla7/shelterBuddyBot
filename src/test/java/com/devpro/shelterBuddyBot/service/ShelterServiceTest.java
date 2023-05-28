package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.model.ShelterBuddy;
import com.devpro.shelterBuddyBot.model.ShelterClients;
import com.devpro.shelterBuddyBot.model.util.Choice;
import com.devpro.shelterBuddyBot.repository.dao.ChoiceDao;
import com.devpro.shelterBuddyBot.repository.dao.ShelterClientsDao;
import com.devpro.shelterBuddyBot.repository.dao.ShelterDao;
import com.devpro.shelterBuddyBot.service.impl.ShelterServiceImpl;
import com.devpro.shelterBuddyBot.util.CallbackRequest;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Contact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShelterServiceTest {
    @Mock
    ChoiceDao choiceDao;
    @Mock
    ShelterDao shelterDao;
    @Mock
    ShelterClientsDao shelterClientsDao;


    @Mock
    InlineKeyboardMarkup inlineKeyboardMarkup;
    private CallbackRequest callbackRequest;
    @Mock
    Choice choice;

    @Mock
    Message message;
    @Mock
    Chat chat;
    @Mock
    Contact contact;
    @Mock
    ShelterClients shelterClient;

    @InjectMocks
    ShelterServiceImpl shelterService;


    @Test
    void testAddButton() {
        shelterService.addButton(inlineKeyboardMarkup, "Button Name", callbackRequest);
        verify(inlineKeyboardMarkup).addRow(any(InlineKeyboardButton.class));
    }

//    @Test
//    void testGetShelterBuddy() {
//        choice = new Choice();
//        choice.setShelterType("CATS");
//        when(choiceDao.findById(123L)).thenReturn(Optional.of(choice));
//        ShelterBuddy shelterBuddy = new ShelterBuddy();
//        shelterBuddy.setShelterId(2);
//        when(shelterDao.findById(2)).thenReturn(Optional.of(shelterBuddy));
//
//        // Call the method under test
//        Optional<ShelterBuddy> result = shelterService.getShelterBuddy(123L);
//
//        // Verify the interactions and assertions
//        assertTrue(result.isPresent());
//        assertEquals(shelterBuddy, result.get());
//        verify(choiceDao, times(1)).findById(123L);
//        verify(shelterDao, times(1)).findById(2);
//        verifyNoMoreInteractions(choiceDao, shelterDao, shelterClientsDao);
//    }

//    @Test
//    void testCheckClientNumber() {
//        ShelterServiceImpl shelterService = new ShelterServiceImpl(choiceDao, shelterDao, shelterClientsDao);
//        when(message.chat().id()).thenReturn(123L);
//        when(contact.phoneNumber()).thenReturn("1234567890");
//        when(message.contact()).thenReturn(contact);
//
//        ShelterBuddy shelterBuddy = new ShelterBuddy();
//        shelterBuddy.setShelterId(1);
//        when(shelterService.getShelterBuddy(123L)).thenReturn(Optional.of(shelterBuddy));
//
//
//        shelterClient = new ShelterClients();
//        when(shelterClientsDao.findByShelterBuddyAndNumberLike(shelterBuddy, "%1234567890")).thenReturn(Optional.of(shelterClient));
//
//
//        boolean result = shelterService.checkClientNumber(message);
//
//
//        assertFalse(result);
//        verify(message, times(1)).chat();
//        verify(message.chat(), times(1)).id();
//        verify(message, times(1)).contact();
//        verify(contact, times(1)).phoneNumber();
//        verify(shelterService, times(1)).getShelterBuddy(123L);
//        verify(shelterClientsDao, times(1)).findByShelterBuddyAndNumberLike(shelterBuddy, "%1234567890");
//        verifyNoMoreInteractions(message, contact, shelterService, shelterClientsDao);
//    }
}

