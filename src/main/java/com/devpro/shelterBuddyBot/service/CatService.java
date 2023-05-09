package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.exeption.NotFoundInBdException;
import com.devpro.shelterBuddyBot.exeption.ValidationException;
import com.devpro.shelterBuddyBot.model.enity.StatusAnimal;
import com.devpro.shelterBuddyBot.model.shelter.Cat;
import com.devpro.shelterBuddyBot.repository.rep.CatRepository;

import java.util.List;

public interface CatService {
    /**
     * Внесение данных о новом животном в БД.
     * Используется метод репозитория {@link CatRepository#save(Object)}
     * @throws ValidationException при ошибке валидации полей создаваемого животного
     * @param cat
     * @return
     */
    Cat createCat(Cat cat);
    /**
     * Поиск животного по его идентификатору в БД.
     * Используется метод репозитория {@link CatRepository#findById(Object)}
     * @throws NotFoundInBdException если животное не найдено в БД
     * @param id
     * @return
     */
    Cat findById(Long id);
    /**
     * Поиск и обновление данных о животном в БД по его идентификатору.
     * Используются методы репозитория {@link CatRepository#findById(Object)} и {@link CatRepository#save(Object)}
     * @throws NotFoundInBdException если животное не найдено в БД
     * @param id - ид обновляемой записи
     * @param cat - на что обновляем
     * @return
     */
    Cat updateById(Long id, Cat cat);
    /**
     * Поиск и удаление данных о животном в БД по его идентификатору.
     * Используются методы репозитория {@link CatRepository#findById(Object)} и {@link CatRepository#deleteById(Object)}
     * @throws NotFoundInBdException если животное не найдено в БД
     * @param id
     * @return
     */
    Cat deleteById(Long id);
    /**
     * Вывод полного списка животных из БД.
     * Используется метод репозитория {@link CatRepository#findAll}
     * @return
     */
    List<Cat> findAll();

    List<Cat> findAllInShelter();

    /**
     * Поиск в БД животного по его имени.
     * Используется метод репозитория {@link CatRepository#findByName(String)}
     * @param name
     * @return
     */
    Cat findByName(String name);

    /**
     * получение списка котов по их статусу
     * @param statusAnimal
     * @return
     */
    List<Cat> showAllByStatus(StatusAnimal statusAnimal);

    Cat reserveCat(String name, String phone);

    Cat changeStatusAnimal(String name, StatusAnimal statusAnimal);
}
