package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.exeption.NotFoundInBdException;
import com.devpro.shelterBuddyBot.exeption.ValidationException;
import com.devpro.shelterBuddyBot.model.enity.TrialPeriod;
import com.devpro.shelterBuddyBot.repository.rep.TrialPeriodRepository;

import java.util.List;

public interface TrialPeriodService {
    /**
     * Внесение испытательных сроков в БД.
     * Используется метод репозитория {@link TrialPeriodRepository#save(Object)}
     * @throws ValidationException при ошибке валидации полей создаваемого испытательного срока
     * @param trialPeriod
     * @return
     */
    TrialPeriod createTrialPeriod(TrialPeriod trialPeriod);
    /**
     * Поиск испытательного срока по его идентификатору в БД.
     * Используется метод репозитория {@link TrialPeriodRepository#findById(Object)}
     * @throws NotFoundInBdException если испытательный срок не найден в БД
     * @param id
     * @return
     */
    TrialPeriod findById(Long id);
    /**
     * Поиск и обновление испытательного срока в БД по его идентификатору.
     * Используются методы репозитория {@link TrialPeriodRepository#findById(Object)} и {@link TrialPeriodRepository#save(Object)}
     * @throws NotFoundInBdException если испытательный срок не найдено в БД
     * @param id - ид обновляемой записи
     * @param trialPeriod - на что обновляем
     * @return
     */
    TrialPeriod updateById(Long id, TrialPeriod trialPeriod);
    /**
     * Поиск и удаление испытательного срока в БД по его идентификатору.
     * Используются методы репозитория {@link TrialPeriodRepository#findById(Object)} и {@link TrialPeriodRepository#deleteById(Object)}
     * @throws NotFoundInBdException если испытательный срок не найден в БД
     * @param id
     * @return
     */
    TrialPeriod deleteById(Long id);
    /**
     * Вывод полного списка испытательных сроков из БД.
     * Используется метод репозитория {@link TrialPeriodRepository#findAll}
     * @return
     */
    List<TrialPeriod> findAll();
}
