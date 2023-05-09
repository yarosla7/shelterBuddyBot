package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.exeption.NotFoundInBdException;
import com.devpro.shelterBuddyBot.exeption.ValidationException;
import com.devpro.shelterBuddyBot.model.shelter.CatOwner;
import com.devpro.shelterBuddyBot.repository.rep.CatOwnerRepository;

import java.util.List;

public interface CatOwnerService {
    /**
     * Внесение данных о новом опекуне кошки в БД.
     * Используется метод репозитория {@link CatOwnerRepository#save(Object)}
     * @throws ValidationException при ошибке валидации полей создаваемого опекуна кошки
     * @param catOwner
     * @return
     */
    CatOwner createCatOwner(CatOwner catOwner);
    /**
     * Поиск опекуна кошки по его идентификатору в БД.
     * Используется метод репозитория {@link CatOwnerRepository#findById(Object)}
     * @throws NotFoundInBdException если опекун кошки не найден в БД
     * @param id
     * @return
     */
    CatOwner findById(Long id);

    /**
     * Поиск и обновление данных об опекуне кошки в БД по его идентификатору.
     * Используются методы репозитория {@link CatOwnerRepository#findById(Object)} и {@link CatOwnerRepository#save(Object)}
     * @throws NotFoundInBdException если опекун кошки не найдено в БД
     * @param id - ид обновляемой записи
     * @param catOwner - на что обновляем
     * @return
     */
    CatOwner updateById(Long id, CatOwner catOwner);

    /**
     * Поиск и удаление данных об опекуне кошки в БД по его идентификатору.
     * Используются методы репозитория {@link CatOwnerRepository#findById(Object)} и {@link CatOwnerRepository#deleteById(Object)}
     * @throws NotFoundInBdException если опекун кошки не найдено в БД
     * @param id
     * @return
     */
    CatOwner deleteById(Long id);
    /**
     * Вывод полного списка опекунов кошек из БД.
     * Используется метод репозитория {@link CatOwnerRepository#findAll}
     * @return
     */
    List<CatOwner> findAll();


    Boolean existsByPhoneNumber(String phoneNumber);
    /**
     * Поиск в БД опекуна кошки по его номеру телефона.
     * Используется метод репозитория {@link CatOwnerRepository#findByPhoneNumber(String)}
     * @param phoneNumber
     * @return
     */
    CatOwner findByPhoneNumber(String phoneNumber);

    CatOwner getAnimalToTrialPeriod(String phoneNumber, String animalName, long trialDays);
}
