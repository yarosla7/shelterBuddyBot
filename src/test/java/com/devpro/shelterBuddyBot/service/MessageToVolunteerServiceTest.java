package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.exeption.NotFoundInBdException;
import com.devpro.shelterBuddyBot.exeption.ValidationException;
import com.devpro.shelterBuddyBot.model.enity.MessageToVolunteer;
import com.devpro.shelterBuddyBot.model.enity.StatusMessage;
import com.devpro.shelterBuddyBot.repository.rep.MessageToVolunteerRepository;
import com.devpro.shelterBuddyBot.service.impl.MessageToVolunteerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MessageToVolunteerServiceTest {
    @Mock
    private MessageToVolunteerRepository messageToVolunteerRepositoryMock;
    @Mock
    private ValidationService validationServiceMock;
    @InjectMocks
    private MessageToVolunteerServiceImpl messageToVolunteerServiceOut;

    private final static Long ID = 1L;
    private final static String SENDER = "sender";
    private final static String TEXT = "text";


    private MessageToVolunteer messageToVolunteer;

    @BeforeEach
    public void init() {
        messageToVolunteer = new MessageToVolunteer(ID, SENDER, TEXT);
    }

    @Test
    @DisplayName("Проверка корректности создания нового сообщения для волонтера")
    void shouldReturnWhenCreateMessageToVolunteer() {
        Mockito.when(validationServiceMock.validate(messageToVolunteer)).thenReturn(true);
        Mockito.when(messageToVolunteerRepositoryMock.save(any())).thenReturn(messageToVolunteer);
        assertEquals(messageToVolunteer, messageToVolunteerServiceOut.createMessageToVolunteer(messageToVolunteer));
    }

    @Test
    @DisplayName("Исключение при вводе некорректного сообщения волонтеру")
    public void shouldThrowValidationExceptionWhenMessageToVolunteerIsNotValid() {

        Mockito.when(validationServiceMock.validate(messageToVolunteer)).thenReturn(false);
        assertThrows(ValidationException.class, () -> messageToVolunteerServiceOut
                .createMessageToVolunteer(messageToVolunteer));
    }

    @Test
    @DisplayName("Поиск сообщения волонтеру по его Id")
    public void shouldFindUserById() {
        Mockito.when(messageToVolunteerRepositoryMock.findById(ID))
                .thenReturn(Optional.of(messageToVolunteer));
        assertEquals(messageToVolunteer, messageToVolunteerServiceOut.findById(ID));
    }

    @Test
    @DisplayName("Исключение при поиске по некорректному ID сообщения для волонтера")
    public void shouldThrowNotFoundInBdExceptionWhenIdIsNotValid() {
        Mockito.when(messageToVolunteerRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundInBdException.class, () -> messageToVolunteerServiceOut.findById(ID));
    }

    @Test
    @DisplayName("Поиск и обновление сообщения для волонтера по его Id")
    public void shouldFindAndUpdateCorrectMessageToVolunteer() {
        Mockito.when(messageToVolunteerRepositoryMock.findById(ID)).thenReturn(Optional.of(messageToVolunteer));
        Mockito.when(messageToVolunteerRepositoryMock.save(any())).thenReturn(messageToVolunteer);
        assertEquals(messageToVolunteer, messageToVolunteerServiceOut.updateById(ID, messageToVolunteer));
    }

    @Test
    @DisplayName("Исключение при обновлении по некорректному Id сообщения для волонтера")
    public void shouldThrowNotFoundInBdExceptionWhenUpdateByIdIsNotValid() {
        Mockito.when(messageToVolunteerRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundInBdException.class, () -> messageToVolunteerServiceOut.updateById(ID, messageToVolunteer));
    }

    @Test
    @DisplayName("Удаление сообщения для волонтера по его Id")
    public void shouldDeleteUserById() {
        Mockito.when(messageToVolunteerRepositoryMock.findById(ID)).thenReturn(Optional.of(messageToVolunteer));
        assertEquals(messageToVolunteer, messageToVolunteerServiceOut.deleteById(ID));
    }

    @Test
    @DisplayName("Вывод списка всех сообщений")
    public void shouldFindByAllCorrectMessageToVolunteer() {
        MessageToVolunteer message1 = new MessageToVolunteer(1L, SENDER, TEXT);
        MessageToVolunteer message2 = new MessageToVolunteer(2L, SENDER, TEXT);
        List<MessageToVolunteer> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);

        Mockito.when(messageToVolunteerRepositoryMock.findAll()).thenReturn(messages);
        List<MessageToVolunteer> result = messageToVolunteerServiceOut.findAll();
        assertEquals(messages, result);
    }

    @Test
    @DisplayName("Поиск непрочитанных сообщений для волонтера")
    void shouldCheckerMessageToVolunteer() {
        List<MessageToVolunteer> messages = new ArrayList<>();
        MessageToVolunteer readMessage = new MessageToVolunteer(1L, SENDER, TEXT);
        readMessage.setStatusMessage(StatusMessage.READ);
        messages.add(readMessage);

        MessageToVolunteer unreadMessage = new MessageToVolunteer(2L, SENDER, TEXT);
        unreadMessage.setStatusMessage(StatusMessage.UNREAD);
        messages.add(unreadMessage);

        Mockito.when(messageToVolunteerRepositoryMock.findAll()).thenReturn(messages);
        boolean result = messageToVolunteerServiceOut.checker();
        assertTrue(result);
    }

}
