package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.model.Animal;
import com.devpro.shelterBuddyBot.model.Reports;

import com.devpro.shelterBuddyBot.model.ShelterClients;
import com.devpro.shelterBuddyBot.model.Volunteer;
import com.devpro.shelterBuddyBot.repository.dao.AnimalDao;
import com.devpro.shelterBuddyBot.repository.dao.ReportsDao;
import com.devpro.shelterBuddyBot.repository.dao.ShelterClientsDao;
import com.devpro.shelterBuddyBot.repository.dao.VolunteerDao;
import com.devpro.shelterBuddyBot.service.impl.AdminServiceImpl;
import com.devpro.shelterBuddyBot.service.impl.ShelterServiceImpl;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
    @Mock
    AnimalDao animalDao;
    @Mock
    ShelterClientsDao shelterClientsDao;
    @Mock
    ShelterServiceImpl shelterService;
    @Mock
    TelegramBot telegramBot;
    @Mock
    VolunteerDao volunteerDao;
    @Mock
    Message message;
    @Mock
    ReportsDao reportsDao;
    @Mock
    Animal animal;
    @InjectMocks
    AdminServiceImpl adminService;
    private LocalDate localDate = LocalDate.now().plusDays(1);

    @Test
    @DisplayName("Вывод всех отчетов")
    void testShowAllReports() {

        Reports report1 = new Reports();
        Reports report2 = new Reports();
        List<Reports> expectedReports = Arrays.asList(report1, report2);
        when(reportsDao.findAllByIsReportOkIsNull()).thenReturn(expectedReports);

        List<Reports> result = adminService.showAllReports();
        verify(reportsDao, times(1)).findAllByIsReportOkIsNull();
        assertEquals(expectedReports, result);
        verifyNoMoreInteractions(reportsDao);
    }

    @Test
    @DisplayName("Добавление волонтера")
    void testAddVolunteer() {
        Long chatId = 12345L;
        String name = "John Doe";

        adminService.addVolunteer(chatId, name);

        verify(volunteerDao, times(1)).save(any(Volunteer.class));
    }

    @Test
    @DisplayName("Проверка счетчика отчетов")
    void testCountReport() {

        Reports report1 = new Reports();
        Reports report2 = new Reports();
        List<Reports> reportsList = Arrays.asList(report1, report2);

        when(adminService.showAllReports()).thenReturn(reportsList);

        String result = adminService.countReport();

        assertEquals("2", result);
    }
    @Test
    @DisplayName("Проверка усыновления животного")
    void testAdoptAnimal() {
        // Define test data
        Integer animalId = 1;
        Integer userId = 1;
        Animal animal = new Animal();
        ShelterClients client = new ShelterClients();
        client.setTookAnimal(false);
        client.setChatId(123);
        animal.setTypeOfAnimal("DOG");
        animal.setPetName("Buddy");
        animal.setBreed("Labrador");

        when(animalDao.findById(animalId)).thenReturn(Optional.of(animal));
        when(shelterClientsDao.findById(userId)).thenReturn(Optional.of(client));

        adminService.adoptAnimal(animalId, userId);

        verify(animalDao, times(1)).findById(animalId);
        verify(shelterClientsDao, times(1)).findById(userId);
        verify(shelterClientsDao, times(1)).save(client);

        assertEquals(false, animal.isInShelter());
        assertEquals(LocalDate.now().plusDays(30), animal.getAdoptDate());
        assertEquals(true, client.getTookAnimal());
        assertEquals(animal, client.getAnimal());
        assertEquals("DOG", animal.getTypeOfAnimal());
        assertEquals("Buddy", animal.getPetName());
        assertEquals("Labrador", animal.getBreed());
    }
}
