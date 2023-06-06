package com.devpro.shelterBuddyBot.controller;

import com.devpro.shelterBuddyBot.controllers.volunteersAPI.AnimalController;
import com.devpro.shelterBuddyBot.model.Animal;
import com.devpro.shelterBuddyBot.repository.dao.AnimalDao;
import com.devpro.shelterBuddyBot.service.AnimalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AnimalControllerTest {
    @Mock
    AnimalService animalService;
    @Mock
    AnimalDao animalDao;
    @InjectMocks
    AnimalController animalController;

    @Test
    @DisplayName("Вывод списка всех животных")
    void testFindAll() {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal());
        animals.add(new Animal());

        when(animalService.findAll()).thenReturn(animals);

        List<Animal> result = animalController.findAll();

        verify(animalService, times(1)).findAll();

        assertEquals(animals, result);

        verifyNoMoreInteractions(animalService);
    }

    @Test
    @DisplayName("Поиск животного по id")
    void testFindById() {

        Animal expectedAnimal = new Animal();


        when(animalService.findById(1)).thenReturn(Optional.of(expectedAnimal));
        when(animalService.findById(2)).thenReturn(Optional.empty());

        ResponseEntity<Animal> response1 = animalController.findById(1);

        verify(animalService, times(1)).findById(1);

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(expectedAnimal, response1.getBody());

        ResponseEntity<Animal> response2 = animalController.findById(2);

        verify(animalService, times(1)).findById(2);

        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());

        verifyNoMoreInteractions(animalService);
    }

    @Test
    @DisplayName("Добавление животного")
    void testAddAnimal() {
        Animal animal = new Animal();

        animalController.addAnimal(animal);
        verify(animalService, times(1)).addAnimal(animal);

        verifyNoMoreInteractions(animalService);
    }

    @Test
    @DisplayName("Редактирование животного")
    void testEditAnimal() {
        Animal updatedAnimal = new Animal();
        updatedAnimal.setAnimalId(1);

        animalController.editAnimal(updatedAnimal);
        verify(animalService, times(1)).editAnimal(updatedAnimal);

        verifyNoMoreInteractions(animalService);
    }


    @Test
    @DisplayName("Удаление животного. Статус 404")
    void testDeleteById_AnimalNotFound() {
        int animalId = 999;

        ResponseEntity<Void> response = animalController.deleteById(animalId);
        verify(animalService, times(1)).findById(animalId);

        verify(animalService, never()).deleteById(anyInt());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verifyNoMoreInteractions(animalService);
    }

}
