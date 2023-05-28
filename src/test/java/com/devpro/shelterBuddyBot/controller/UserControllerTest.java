package com.devpro.shelterBuddyBot.controller;


import com.devpro.shelterBuddyBot.ShelterBuddyBotApplication;
import com.devpro.shelterBuddyBot.controllers.volunteersAPI.UsersController;
import com.devpro.shelterBuddyBot.model.ShelterClients;
import com.devpro.shelterBuddyBot.repository.dao.ShelterClientsDao;
import com.devpro.shelterBuddyBot.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    ShelterClientsDao shelterClientsDao;
    @Mock
    UserServiceImpl userService;
    private ShelterClients user;
    private Integer userId = 1;

    @InjectMocks
    UsersController usersController;
    private static List<ShelterClients> shelterClientsList = List.of(new ShelterClients());

    @BeforeEach
    public void init() {
        user = new ShelterClients();
    }

    @Test
    @DisplayName("Вывод списка всех животных")
    void testFindAll() {
        when(userService.findAll()).thenReturn(shelterClientsList);

        List<ShelterClients> result = usersController.findAll();

        assertEquals(shelterClientsList, result);
        verify(userService, times(1)).findAll();
        verifyNoMoreInteractions(userService);
    }

    @Test
    @DisplayName("Поиск пользователя по id = пользователь найден")
    void testFindById_UserFound() {
        when(userService.findById(1)).thenReturn(Optional.of(user));

        ResponseEntity<ShelterClients> response = usersController.findById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).findById(1);
        verifyNoMoreInteractions(userService);
    }

    @Test
    @DisplayName("Поиск пользователя по id = пользователь не найден")
    void testFindById_UserNotFound() {
        when(userService.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<ShelterClients> response = usersController.findById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).findById(1);
        verifyNoMoreInteractions(userService);
    }

    @Test
    @DisplayName("Редактирование Пользователя")
    void testEditAnimal() {

        usersController.editAnimal(user);

        ArgumentCaptor<ShelterClients> captor = ArgumentCaptor.forClass(ShelterClients.class);
        verify(userService, times(1)).editUser(captor.capture());

        ShelterClients capturedShelterClients = captor.getValue();
        assertEquals(user, capturedShelterClients);


        verifyNoMoreInteractions(userService);
    }

    @Test
    @DisplayName("Удаление пользователя. Сценарий = 200")
    void testDeleteById_UserExists() {
        when(userService.findById(userId)).thenReturn(Optional.of(new ShelterClients()));

        ResponseEntity<Void> response = usersController.deleteById(userId);

        verify(userService, times(1)).findById(userId);

        verify(userService, times(1)).deleteById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertTrue(response.getBody() == null);

        verifyNoMoreInteractions(userService);
    }

    @Test
    @DisplayName("Удаление пользователя. Сценарий = 404")
    void testDeleteById_UserNotFound() {

        when(userService.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = usersController.deleteById(userId);

        verify(userService, times(1)).findById(userId);

        verify(userService, never()).deleteById(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() == null);

        verifyNoMoreInteractions(userService);
    }

    @Test
    void testDeleteAllUsers() {
        ResponseEntity<Void> response = usersController.deleteAllUsers();

        verify(userService, times(1)).deleteAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() == null);

        verifyNoMoreInteractions(userService);
    }

}
