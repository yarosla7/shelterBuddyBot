package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.model.Animal;
import com.devpro.shelterBuddyBot.repository.dao.AnimalDao;
import com.devpro.shelterBuddyBot.service.impl.AnimalServiceImpl;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {

    @Mock
    AnimalDao animalDaoMock;

    @InjectMocks
    private AnimalServiceImpl animalService;

    private Animal animal;
    private static List<Animal> ANIMALS = List.of(new Animal());
    private Integer anId = 1;

    @BeforeEach
    public void init() {
        animal = new Animal();
    }

    @Test
    @DisplayName("Вывод списка всех животных")
    public void findAll() {
        Mockito.when(animalDaoMock.findAll()).thenReturn(ANIMALS);
        assertEquals(ANIMALS, animalService.findAll());
    }

    @Test
    @DisplayName("Поиск животного по ID")
    public void findById() {
        Mockito.when(animalDaoMock.findById(anId)).thenReturn(Optional.of(animal));
        assertEquals(Optional.of(animal), animalService.findById(anId));
    }

    @Test
    @DisplayName("Проверка корректного создания нового животного")
    public void shouldReturnWhenCreateNewUser() {
        Mockito.when(animalDaoMock.save(any())).thenReturn(animal);
        assertEquals(animal, animalService.addAnimal(animal));
        verify(animalDaoMock, times(1)).save(any());
    }

    @Test
    @DisplayName("Удаление животного")
    public void deleteById() {
        Mockito.when(animalDaoMock.findById(anId)).thenReturn(Optional.of(animal));
        assertEquals(Optional.of(animal), animalService.deleteById(anId));
    }

    @Test
    @DisplayName("Проверка редактирования животного")
    public void editAnimal() {
        Mockito.when(animalDaoMock.save(any())).thenReturn(animal);
        assertEquals((animal), animalService.editAnimal(animal));
    }

}
