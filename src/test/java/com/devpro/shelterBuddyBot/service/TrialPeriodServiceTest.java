package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.exeption.NotFoundInBdException;
import com.devpro.shelterBuddyBot.exeption.ValidationException;
import com.devpro.shelterBuddyBot.model.enity.TrialPeriod;
import com.devpro.shelterBuddyBot.repository.rep.TrialPeriodRepository;
import com.devpro.shelterBuddyBot.service.impl.TrialPeriodServiceImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class TrialPeriodServiceTest {
    @Mock
    private TrialPeriodRepository trialPeriodRepositoryMock;
    @Mock
    private ValidationService validationServiceMock;
    @InjectMocks
    private TrialPeriodServiceImpl trialPeriodServiceOut;

    private Long ID = 1L;
    private String OWNER_NAME = "ownerName";
    private static List<TrialPeriod> TRIAL_PERIODS = List.of(new TrialPeriod());
    private TrialPeriod trialPeriod;


    @BeforeEach
    public void init() {
        trialPeriod = new TrialPeriod();
    }

    @Test
    @DisplayName("Проверка корректности создания испытательного периода")
    void shouldReturnWhenCreateTrialPeriod() {
        Mockito.when(validationServiceMock.validate(trialPeriod)).thenReturn(true);
        Mockito.when(trialPeriodRepositoryMock.save(any())).thenReturn(trialPeriod);
        assertEquals(trialPeriod, trialPeriodServiceOut.createTrialPeriod(trialPeriod));
    }
    @Test
    @DisplayName("Исключение при вводе некорректного испытательного периода")
    public void shouldThrowValidationExceptionWhenMessageToVolunteerIsNotValid() {
        Mockito.when(validationServiceMock.validate(trialPeriod)).thenReturn(false);
        assertThrows(ValidationException.class, () -> trialPeriodServiceOut
                .createTrialPeriod(trialPeriod));
    }
    @Test
    @DisplayName("Поиск и обновление испытательного периода по его Id")
    public void shouldFindAndUpdateCorrectTrialPeriod() {
        Mockito.when(trialPeriodRepositoryMock.findById(ID)).thenReturn(Optional.of(trialPeriod));
        Mockito.when(trialPeriodRepositoryMock.save(any())).thenReturn(trialPeriod);
        assertEquals(trialPeriod, trialPeriodServiceOut.updateById(ID, trialPeriod));

    }
    @Test
    @DisplayName("Исключение при поиске испытательного периода по некорректному ID")
    public void shouldThrowNotFoundInBdExceptionWhenIdIsNotValid() {
        Mockito.when(trialPeriodRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundInBdException.class, () -> trialPeriodServiceOut.findById(ID));
    }
    @Test
    @DisplayName("Удаление отчета по его Id")
    public void shouldDeleteUserById() {
        Mockito.when(trialPeriodRepositoryMock.findById(ID)).thenReturn(Optional.of(trialPeriod));
        assertEquals(trialPeriod, trialPeriodServiceOut.deleteById(ID));
    }
    @Test
    @DisplayName("Вывод списка всех отчетов")
    public void shouldFindByAllCorrectReport() {
        Mockito.when(trialPeriodRepositoryMock.findAll()).thenReturn(TRIAL_PERIODS);
        assertEquals(TRIAL_PERIODS, trialPeriodServiceOut.findAll());
    }
}
