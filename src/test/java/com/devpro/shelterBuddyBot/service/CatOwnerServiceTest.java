package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.exeption.NotFoundInBdException;
import com.devpro.shelterBuddyBot.exeption.ValidationException;
import com.devpro.shelterBuddyBot.model.enity.Report;
import com.devpro.shelterBuddyBot.model.enity.TrialPeriod;
import com.devpro.shelterBuddyBot.model.shelter.Cat;
import com.devpro.shelterBuddyBot.model.shelter.CatOwner;
import com.devpro.shelterBuddyBot.repository.rep.CatOwnerRepository;
import com.devpro.shelterBuddyBot.service.ValidationService;
import com.devpro.shelterBuddyBot.service.impl.CatOwnerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class CatOwnerServiceTest {
    @Mock
    private CatOwnerRepository catOwnerRepositoryMock;
    @Mock
    private ValidationService validationServiceMock;


    @InjectMocks
    private CatOwnerServiceImpl catOwnerServiceOut;

    private static Long ID = 1L;
    private static String PHONE_NUMBER = "+79053930303";


    private static List<Report> REPORTS = List.of(new Report());
    private static List<TrialPeriod> TRIAL_PERIODS_IN_ACTIVE_STATUS = List.of(new TrialPeriod());
    private static List<TrialPeriod> TRIAL_PERIODS_COMPLETED = List.of(new TrialPeriod());
    private static List<Cat> CATS = List.of(new Cat());
    private static List<CatOwner> CAT_OWNER = List.of(new CatOwner());


    private CatOwner catOwner;

    @BeforeEach
    public void init() {
        catOwner = new CatOwner(ID, PHONE_NUMBER, CATS, TRIAL_PERIODS_IN_ACTIVE_STATUS, TRIAL_PERIODS_COMPLETED);
    }

    @Test
    @DisplayName("Проверка корректного создания владельца кошки")
    void shouldReturnWhenCreateNewCatOwner() {
        when(validationServiceMock.validate(catOwner)).thenReturn(true);
        when(catOwnerRepositoryMock.save(any())).thenReturn(catOwner);
        assertEquals(catOwner, catOwnerServiceOut.createCatOwner(catOwner));
    }

    @Test
    @DisplayName("Исключение при некорректной валидации владельца кошки")
    void shouldThrowValidationExceptionWhenValidateNotValid() {
        when(validationServiceMock.validate(catOwner)).thenReturn(false);
        assertThrows(ValidationException.class, () -> catOwnerServiceOut.createCatOwner(catOwner));

    }

    @Test
    @DisplayName("Поиск владельца кошки по его Id")
    void shouldFindCatOwnerById() {
        when(catOwnerRepositoryMock.findById(any())).thenReturn(Optional.of(catOwner));
        assertEquals(catOwner, catOwnerServiceOut.findById(ID));
    }

    @Test
    @DisplayName("Исключение при поиске владельца кошки по некорректному Id")
    void shouldThrowNotFoundInBdExceptionWhenIdCatOwnerIsNotValid() {
        when(catOwnerRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundInBdException.class, () -> catOwnerServiceOut.findById(ID));
    }

    @Test
    @DisplayName("Поиск и обновление владельца кошки по его Id")
    public void shouldFindAndUpdateCorrectCatOwner() {
        when(catOwnerRepositoryMock.findById(ID)).thenReturn(Optional.of(catOwner));
        when(catOwnerRepositoryMock.save(any())).thenReturn(catOwner);
        assertEquals(catOwner, catOwnerServiceOut.updateById(ID, catOwner));
    }

    @Test
    @DisplayName("Исключение при обновлении владельца кошки по некорректному Id")
    public void shouldThrowNotFoundInBdExceptionWhenUpdateCatOwnerByIdIsNotValid() {
        when(catOwnerRepositoryMock.findById(ID)).thenReturn(Optional.empty());
        assertThrows(NotFoundInBdException.class, () -> catOwnerServiceOut.updateById(ID, catOwner));
    }

    @Test
    @DisplayName("Поиск владельца кошки по его номеру телефона")
    void shouldFindCatOwnerByPhoneNumber() {
        when(catOwnerRepositoryMock.findByPhoneNumber(PHONE_NUMBER)).thenReturn(Optional.of(catOwner));
        assertEquals(catOwner, catOwnerServiceOut.findByPhoneNumber(PHONE_NUMBER));
    }


    @Test
    @DisplayName("Проверка удаления владельца кошки")
    void shouldReturnWhenDeleteCatOwner() {
        when(catOwnerRepositoryMock.findById(any())).thenReturn(Optional.of(catOwner));
        assertEquals(catOwner, catOwnerServiceOut.deleteById(ID));
    }

    @Test
    @DisplayName("Вывод списка всех владельцев кошек")
    public void shouldFindAllCatOwners() {
        when(catOwnerServiceOut.findAll()).thenReturn(CAT_OWNER);
        assertEquals(CAT_OWNER, catOwnerServiceOut.findAll());
    }

    @Test
    @DisplayName("Проверка существования номера телефона")
    public void shouldReturnExistPhoneNumberCatOwners() {
        boolean expectedExists = true;
        when(catOwnerServiceOut.existsByPhoneNumber(PHONE_NUMBER)).thenReturn(expectedExists);

        boolean actualExists = catOwnerServiceOut.existsByPhoneNumber(PHONE_NUMBER);
        assertEquals(expectedExists, actualExists);
        verify(catOwnerRepositoryMock).existsByPhoneNumber(PHONE_NUMBER);
    }

}
