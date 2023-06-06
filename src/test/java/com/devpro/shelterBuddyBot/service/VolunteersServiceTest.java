package com.devpro.shelterBuddyBot.service;


import com.devpro.shelterBuddyBot.model.Animal;
import com.devpro.shelterBuddyBot.model.Volunteer;
import com.devpro.shelterBuddyBot.repository.dao.VolunteerDao;
import com.devpro.shelterBuddyBot.service.impl.VolunteersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class VolunteersServiceTest {

    @Mock
    VolunteerDao volunteerDaoMock;

    @InjectMocks
    private VolunteersServiceImpl volunteersService;

    private Volunteer volunteer;
    private static List<Volunteer> VOLUNTEERS = List.of(new Volunteer());
    private Long chatId = 1L;
    private Integer id = 1;

    @BeforeEach
    public void init() {
        volunteer = new Volunteer();
    }

    @Test
    @DisplayName("Вывод всех волонтеров")
    public void findAll() {
        Mockito.when(volunteerDaoMock.findAll()).thenReturn(VOLUNTEERS);
        assertEquals(VOLUNTEERS,volunteersService.findAll());
    }


    @Test
    @DisplayName("Добавление нового волонтера")
    public void addVolunteer() {
        Mockito.when(volunteerDaoMock.save(any())).thenReturn(volunteer);
        assertEquals(volunteer, volunteersService.addVolunteer(volunteer));
        verify(volunteerDaoMock, times(1)).save(any());
    }

    @Test
    @DisplayName("Удаление волонтера")
    public void deleteById() {
        Mockito.when(volunteerDaoMock.findById(chatId)).thenReturn(Optional.of(volunteer));
        assertEquals(Optional.of(volunteer), volunteersService.deleteById(chatId));
    }

}
