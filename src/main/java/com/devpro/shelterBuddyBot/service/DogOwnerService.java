package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.exeption.NotFoundInBdException;
import com.devpro.shelterBuddyBot.exeption.ValidationException;
import com.devpro.shelterBuddyBot.model.shelter.DogOwner;
import com.devpro.shelterBuddyBot.repository.rep.DogOwnerRepository;

import java.util.List;

public interface DogOwnerService {
    /**
     * Внесение данных о новом опекуне собаки в БД.
     * Используется метод репозитория {@link DogOwnerRepository#save(Object)}
     * @throws ValidationException при ошибке валидации полей создаваемого опекуна собаки
     * @param dogOwner
     * @return
     */
    DogOwner createDogOwner(DogOwner dogOwner);

    /**
     * Поиск опекуна собаки по его идентификатору в БД.
     * Используется метод репозитория {@link DogOwnerRepository#findById(Object)}
     * @throws NotFoundInBdException если опекун собаки не найден в БД
     * @param id
     * @return
     */
    DogOwner findById(Long id);

    /**
     * Поиск и обновление данных об опекуне собаки в БД по его идентификатору.
     * Используются методы репозитория {@link DogOwnerRepository#findById(Object)} и {@link DogOwnerRepository#save(Object)}
     * @throws NotFoundInBdException если опекун собаки не найдено в БД
     * @param id - ид обновляемой записи
     * @param dogOwner - на что обновляем
     * @return
     */
    DogOwner updateById(Long id, DogOwner dogOwner);

    /**
     * Поиск и удаление данных об опекуне собаки в БД по его идентификатору.
     * Используются методы репозитория {@link DogOwnerRepository#findById(Object)} и {@link DogOwnerRepository#deleteById(Object)}
     * @throws NotFoundInBdException если опекун собаки не найдено в БД
     * @param id
     * @return
     */
    DogOwner deleteById(Long id);

    /**
     * Вывод полного списка опекунов собак из БД.
     * Используется метод репозитория {@link DogOwnerRepository#findAll}
     * @return
     */
    List<DogOwner> findAll();

    Boolean existsByPhoneNumber(String phoneNumber);
    /**
     * Поиск в БД опекуна собаки по его номеру телефона.
     * Используется метод репозитория {@link DogOwnerRepository#findByPhoneNumber(String)}
     * @param phoneNumber
     * @return
     */
    DogOwner findByPhoneNumber(String phoneNumber);

    DogOwner getAnimalToTrialPeriod(String phoneNumber, String animalName, long trialDays);
}
