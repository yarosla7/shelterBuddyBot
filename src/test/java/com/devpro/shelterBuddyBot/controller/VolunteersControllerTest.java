package com.devpro.shelterBuddyBot.controller;

import com.devpro.shelterBuddyBot.controllers.volunteersAPI.VolunteersController;
import com.devpro.shelterBuddyBot.model.Volunteer;
import com.devpro.shelterBuddyBot.repository.dao.VolunteerDao;
import com.devpro.shelterBuddyBot.service.impl.VolunteersServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VolunteersControllerTest {

    @Mock
    VolunteerDao volunteerDao;
    @Mock
    VolunteersServiceImpl volunteersService;
    @InjectMocks
    VolunteersController volunteersController;
    private Volunteer volunteer = new Volunteer();



    @Test
    @DisplayName("Вывод списка всех волонтеров")
    void testFindAll() {
        List<Volunteer> expectedVolunteers = new ArrayList<>();

        when(volunteersService.findAll()).thenReturn(expectedVolunteers);
        List<Volunteer> result = volunteersController.findAll();

        verify(volunteersService, times(1)).findAll();
        assertEquals(expectedVolunteers, result);

        verifyNoMoreInteractions(volunteersService);
    }

    @Test
    @DisplayName("Поиск волонтера по id")
    void testFindById() {
        Long volunteerId = 1L;

        Volunteer expectedVolunteer = new Volunteer();
        expectedVolunteer.setChatId(volunteerId);

        when(volunteersService.findById(volunteerId)).thenReturn(Optional.of(expectedVolunteer));

        ResponseEntity<Volunteer> result = volunteersController.findById(volunteerId);

        verify(volunteersService, times(1)).findById(volunteerId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedVolunteer, result.getBody());

        verifyNoMoreInteractions(volunteersService);
    }

    @Test
    @DisplayName("Добавление нового волонтера")
    void testAddVolunteer() {
        volunteersController.addVolunteer(volunteer);
        verify(volunteersService, times(1)).addVolunteer(volunteer);

        verifyNoMoreInteractions(volunteersService);
    }

    @Test
    @DisplayName("Удаление волонтера")
    void testDeleteById() {
        Long id = 123L;
        when(volunteersService.findById(id)).thenReturn(Optional.of(new Volunteer()));

        volunteersController.deleteById(id);
        verify(volunteersService, times(1)).deleteById(id);

        verifyNoMoreInteractions(volunteersService);
    }
}
