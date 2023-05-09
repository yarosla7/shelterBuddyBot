package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.exeption.NotFoundInBdException;
import com.devpro.shelterBuddyBot.exeption.ValidationException;
import com.devpro.shelterBuddyBot.model.enity.StatusAnimal;
import com.devpro.shelterBuddyBot.model.shelter.Dog;
import com.devpro.shelterBuddyBot.repository.rep.DogRepository;
import com.devpro.shelterBuddyBot.service.impl.DogServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class DogServiceImplTest {
    private static final Dog VALID_DOG = new Dog(1L,"Шарик", 12, true, true, StatusAnimal.HAS_HOUSE);
    private static final Dog INVALID_DOG = new Dog(2L,"Жук", 0, true, true, StatusAnimal.IN_THE_SHELTER);

    @Mock
    DogRepository dogRepositoryMock;
    @Mock
    private ValidationService validationServiceMock;
    @InjectMocks
    DogServiceImpl dogServiceOut;
    private static Long ID = 1L;
    private Dog dog;
    @BeforeEach
    public void init() {
        dog = new Dog();
    }
    private List<Dog> DOG = List.of(new Dog());

    @Test
    @DisplayName("Проверка корректного создания новой собаки")
    public void shouldReturnWhenCreateNewDog() {

        Mockito.when(validationServiceMock.validate(dog)).thenReturn(true);
        Mockito.when(dogRepositoryMock.save(any())).thenReturn(dog);
        assertEquals(dog, dogServiceOut.createDog(dog));
        verify(dogRepositoryMock, times(1)).save(any());
    }
    @Test
    @DisplayName("Поиск собаки по его Id")
    public void shouldFindDogById() {
        Mockito.when(dogRepositoryMock.findById(ID)).thenReturn(Optional.of(dog));
        assertEquals(dog, dogServiceOut.findById(ID));
    }

    @Test
    @DisplayName("Исключение при поиске собакина по некорректному ID")
    public void shouldThrowNotFoundInBdExceptionWhenIdIsNotValid() {
        Mockito.when(dogRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundInBdException.class, () -> dogServiceOut.findById(ID));
    }
    @Test
    @DisplayName("Возвращает false если собаки с именем нет в списке")
    void inReturnFalse() {
        Mockito.when(validationServiceMock.validate(dog)).thenReturn(false);
        assertThrows(ValidationException.class, () -> dogServiceOut.createDog(dog));
    }
    @Test
    @DisplayName("Исключение при обновлении по некорректному Id собакина")
    public void shouldThrowNotFoundInBdExceptionWhenUpdateByIdIsNotValid() {
        Mockito.when(dogRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundInBdException.class, () -> dogServiceOut.updateById(ID, dog));
    }
    @Test
    @DisplayName("Вывод списка всех собачек")
    public void shouldFindByAllUsers(){
        Mockito.when(dogRepositoryMock.findAll()).thenReturn(DOG);
        assertEquals(DOG, dogServiceOut.findAll());
    }
    @Test
    @DisplayName("Исключение при некорректной валидации владельца кошки")
    void shouldThrowValidationExceptionWhenValidateNotValid() {
        Mockito.when(validationServiceMock.validate(dog)).thenReturn(false);
        assertThrows(ValidationException.class, () -> dogServiceOut.createDog(dog));

    }
    @Test
    @DisplayName("Исключение при поиске владельца кошки по некорректному Id")
    void shouldThrowNotFoundInBdExceptionWhenIdCatOwnerIsNotValid() {
        Mockito.when(dogRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundInBdException.class,()->dogServiceOut.findById(ID));
    }
    @Test
    @DisplayName("Поиск владельца кошки по его имени")
    void shouldFindCatOwnerById() {
        Mockito.when(dogRepositoryMock.findByName(any())).thenReturn(Optional.of(dog));
        assertEquals(dog, dogServiceOut.findByName(dog.getName()));
    }
    @Test
    @DisplayName("Проверка удаления котиков")
    void shouldReturnWhenDeleteCatOwner() {
        Mockito.when(dogRepositoryMock.findById(any())).thenReturn(Optional.of(dog));
        assertEquals(dog, dogServiceOut.deleteById(ID));
    }
}
