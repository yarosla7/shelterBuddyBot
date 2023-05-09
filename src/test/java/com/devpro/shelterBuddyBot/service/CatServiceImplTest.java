package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.exeption.NotFoundInBdException;
import com.devpro.shelterBuddyBot.exeption.ValidationException;
import com.devpro.shelterBuddyBot.model.enity.StatusAnimal;
import com.devpro.shelterBuddyBot.model.shelter.Cat;
import com.devpro.shelterBuddyBot.repository.rep.CatRepository;
import com.devpro.shelterBuddyBot.service.impl.CatServiceImpl;
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
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CatServiceImplTest {
    private static final Cat VALID_CAT = new Cat("Барсик", 12, true, true, StatusAnimal.HAS_HOUSE);
    private static final Cat INVALID_CAT = new Cat("Жук", 0, true, true, StatusAnimal.IN_THE_SHELTER);

    @Mock
    CatRepository catRepositoryMock;
    @Mock
    private ValidationService validationServiceMock;
    @InjectMocks
    CatServiceImpl catServiceOut;
    private static Long ID = 1L;

    private Cat cat;
    private static List<Cat> CAT = List.of(new Cat());
    @BeforeEach
    public void init() {
        cat = new Cat();
    }
    @Test
    @DisplayName("Проверка корректного создания нового котика")
    public void shouldReturnWhenCreateNewUCat() {

        Mockito.when(validationServiceMock.validate(cat)).thenReturn(true);
        Mockito.when(catRepositoryMock.save(any())).thenReturn(cat);
        assertEquals(cat, catServiceOut.createCat(cat));
        verify(catRepositoryMock, times(1)).save(any());
    }
    @Test
    @DisplayName("Поиск котика по его Id")
    public void shouldFindCatById() {
        Mockito.when(catRepositoryMock.findById(ID)).thenReturn(Optional.of(cat));
        assertEquals(cat, catServiceOut.findById(ID));
    }
    @Test
    @DisplayName("Исключение при поиске котика по некорректному ID")
    public void shouldThrowNotFoundInBdExceptionWhenIdIsNotValid() {
        Mockito.when(catRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundInBdException.class, () -> catServiceOut.findById(ID));
    }
    @Test
    @DisplayName("Поиск и обновление котика по его Id")
    public void shouldFindAndUpdateCorrectCat() {
        Mockito.when(catRepositoryMock.findById(ID)).thenReturn(Optional.of(cat));
        Mockito.when(catRepositoryMock.save(any())).thenReturn(cat);
        assertEquals(cat, catServiceOut.updateById(ID, cat));
    }
    @Test
    @DisplayName("Возвращает false если котика с именем нет в списке")
    void shouldReturnFalse() {
        Mockito.when(validationServiceMock.validate(cat)).thenReturn(false);
        assertThrows(ValidationException.class, () -> catServiceOut.createCat(cat));
    }
    @Test
    @DisplayName("Исключение при обновлении по некорректному Id котика")
    public void shouldThrowNotFoundInBdExceptionWhenUpdateByIdIsNotValid() {
        Mockito.when(catRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundInBdException.class, () -> catServiceOut.updateById(ID, cat));
    }
    @Test
    @DisplayName("Вывод списка всех котиков")
    public void shouldFindByAllUsers(){
        Mockito.when(catRepositoryMock.findAll()).thenReturn(CAT);
        assertEquals(CAT, catServiceOut.findAll());
    }
    @Test
    @DisplayName("Исключение при поиске владельца собаки по некорректному Id")
    void shouldThrowNotFoundInBdExceptionWhenIdDogOwnerIsNotValid() {
        Mockito.when(catRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundInBdException.class, () -> catServiceOut.findById(ID));
    }
    @Test
    @DisplayName("Проверка удаления котиков")
    void shouldReturnWhenDeleteCatOwner() {
        Mockito.when(catRepositoryMock.findById(any())).thenReturn(Optional.of(cat));
        assertEquals(cat, catServiceOut.deleteById(ID));
    }
    @Test
    @DisplayName("Исключение при некорректной валидации владельца кошки")
    void shouldThrowValidationExceptionWhenValidateNotValid() {
        Mockito.when(validationServiceMock.validate(cat)).thenReturn(false);
        assertThrows(ValidationException.class, () -> catServiceOut.createCat(cat));

    }
    @Test
    @DisplayName("Исключение при поиске владельца кошки по некорректному Id")
    void shouldThrowNotFoundInBdExceptionWhenIdCatOwnerIsNotValid() {
        Mockito.when(catRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundInBdException.class,()->catServiceOut.findById(ID));
    }
    @Test
    @DisplayName("Поиск владельца кошки по его имени")
    void shouldFindCatOwnerById() {
        Mockito.when(catRepositoryMock.findByName(any())).thenReturn(Optional.of(cat));
        assertEquals(cat, catServiceOut.findByName(cat.getName()));
    }

}
