package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.model.Animal;
import com.devpro.shelterBuddyBot.repository.dao.AnimalDao;
import com.devpro.shelterBuddyBot.service.impl.AnimalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

import java.util.List;
import java.util.Optional;

import static org.springframework.test.util.AssertionErrors.assertEquals;

public class AnimalServiceTest {

    @Mock
    AnimalDao animalDaoMock;
    @InjectMocks
    AnimalServiceImpl animalServiceOut;


    private Animal animal;
    private static List<Animal> ANIMAL = List.of(new Animal());
    private final Integer ID = 1;

    @BeforeEach
    public void init() {
        animal = new Animal();
    }

//    @Test
//    @DisplayName("Поиск животного по его Id")
//    public void shouldFindCatById() {
//        Mockito.when(animalDaoMock.findById(ID)).thenReturn(Optional.of(animal));
//        assertEquals(Optional.of(animal), animalServiceOut.findById(ID));
//    }

//    @Test void тип?
//    @DisplayName("Поиск и обновление животного по его Id")
//    public void shouldFindAndUpdateCorrectCat() {
//        Mockito.when(animalDaoMock.findById(ID)).thenReturn(Optional.of(animal));
//        Mockito.when(animalDaoMock.save(any())).thenReturn(animal);
//        assertEquals(animal, animalServiceOut.editAnimal(animal));
//    }

//    @Test void тип?
//    @DisplayName("Проверка удаления котиков")
//    void shouldReturnWhenDeleteCatOwner() {
//        Mockito.when(animalDaoMock.findById(any())).thenReturn(Optional.of(animal));
//        assertEquals(animal, animalServiceOut.deleteById(ID));
//    }



}
