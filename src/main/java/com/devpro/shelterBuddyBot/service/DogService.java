package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.exeption.NotFoundInBdException;
import com.devpro.shelterBuddyBot.exeption.ValidationException;
import com.devpro.shelterBuddyBot.model.enity.StatusAnimal;
import com.devpro.shelterBuddyBot.model.shelter.Dog;
import com.devpro.shelterBuddyBot.repository.rep.DogRepository;

import java.util.List;

public interface DogService {
    /**
     * Внесение данных о новом животном в БД.
     * Используется метод репозитория {@link DogRepository#save(Object)}
     * @throws ValidationException при ошибке валидации полей создаваемого животного
     * @param dog
     * @return
     */
    Dog createDog(Dog dog);
    /**
     * Поиск животного по его идентификатору в БД.
     * Используется метод репозитория {@link DogRepository#findById(Object)}
     * @throws NotFoundInBdException если животное не найдено в БД
     * @param id
     * @return
     */
    Dog findById(Long id);
    /**
     * Поиск и обновление данных о животном в БД по его идентификатору.
     * Используются методы репозитория {@link DogRepository#findById(Object)} и {@link DogRepository#save(Object)}
     * @throws NotFoundInBdException если животное не найдено в БД
     * @param id - ид обновляемой записи
     * @param dog - на что обновляем
     * @return
     */
    Dog updateById(Long id, Dog dog);
    /**
     * Поиск и удаление данных о животном в БД по его идентификатору.
     * Используются методы репозитория {@link DogRepository#findById(Object)} и {@link DogRepository#deleteById(Object)}
     * @throws NotFoundInBdException если животное не найдено в БД
     * @param id
     * @return
     */
    Dog deleteById(Long id);
    /**
     * Вывод полного списка животных из БД.
     * Используется метод репозитория {@link DogRepository#findAll}
     * @return
     */
    List<Dog> findAll();

    List<Dog> findAllInShelter();

    /**
     * Поиск в БД животного по его имени.
     * Используется метод репозитория {@link DogRepository#findByName(String)}
     * @param name
     * @return
     */
    Dog findByName(String name);

    List<Dog> showAllByStatus(StatusAnimal statusAnimal);

    //404, 405, 409
    Dog reserveDog(String name, String phone);

    Dog changeStatusAnimal(String name, StatusAnimal statusAnimal);
}
