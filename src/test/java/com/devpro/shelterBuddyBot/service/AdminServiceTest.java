package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.model.Animal;
import com.devpro.shelterBuddyBot.model.Reports;

import com.devpro.shelterBuddyBot.repository.dao.AnimalDao;
import com.devpro.shelterBuddyBot.repository.dao.ReportsDao;
import com.devpro.shelterBuddyBot.repository.dao.ShelterClientsDao;
import com.devpro.shelterBuddyBot.repository.dao.VolunteerDao;
import com.devpro.shelterBuddyBot.service.impl.AdminServiceImpl;
import com.devpro.shelterBuddyBot.service.impl.ShelterServiceImpl;
import com.pengrad.telegrambot.TelegramBot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;


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
    ReportsDao reportsDao;
    @Mock
    Animal animal;
    @Mock
    AdminServiceImpl adminService;

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



}
