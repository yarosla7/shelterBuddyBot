package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.model.ShelterClients;
import com.devpro.shelterBuddyBot.repository.dao.ShelterClientsDao;
import com.devpro.shelterBuddyBot.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    ShelterClientsDao shelterClientsDaoMock;

    @InjectMocks
    private UserServiceImpl userServiceOut;

    private Integer userId = 1;

    private String name = "bot";

    private String number = "+79053930303";

    private Boolean tookAnimal = false;

    private Integer chatId = 1;
    private ShelterClients user;
    private static List<ShelterClients> USERS = List.of(new ShelterClients());

    @BeforeEach
    public void init() {
        user = new ShelterClients();
    }

    @Test
    @DisplayName("Поиск пользователя по его Id")
    public void shouldFindUserById() {
        Mockito.when(shelterClientsDaoMock.findById(userId)).thenReturn(Optional.of(user));
        assertEquals(Optional.of(user), userServiceOut.findById(userId));
    }

    @Test
    @DisplayName("Вывод списка всех пользователей")
    public void shouldFindAllUsers() {
        Mockito.when(shelterClientsDaoMock.findAll()).thenReturn(USERS);
        assertEquals(USERS, userServiceOut.findAll());
    }

    @Test
    @DisplayName("Удаление пользователя по его Id")
    public void shouldDeleteUserById() {
        Mockito.when(shelterClientsDaoMock.findById(chatId)).thenReturn(Optional.of(user));
        assertEquals(Optional.of(user), userServiceOut.deleteById(chatId));
    }

//    @Test
//    @DisplayName("Поиск и обновление пользователя по его Id")
//    public void shouldFindAndUpdateCorrectUser() {
//        Mockito.when(shelterClientsDaoMock.save(any())).thenReturn(user);
//        assertEquals(user, userServiceOut.editUser(user));
//    }
}
