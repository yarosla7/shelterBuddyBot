package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.model.ShelterClients;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для работы с базой данных пользователей приютов ShelterBuddy.class
 */
public interface UserService {

    /**
     * Находит всех пользователей.
     *
     * @return список всех пользователей
     */
    List<ShelterClients> findAll();

    /**
     * Находит пользователя по id.
     *
     * @param id идентификатор пользователя
     * @return пользователь с заданным идентификатором Optional,
     * возвращает пустой Optional, если пользователь не найден
     */
    Optional<ShelterClients> findById(Integer id);

    /**
     * Редактирует информацию о пользователе.
     *
     * @param shelterClient пользователь с новыми данными
     * @return пользователь с заданным идентификатором Optional,
     *  возвращает пустой Optional, если пользователь не найден
     */
    ShelterClients editUser(ShelterClients shelterClient);

    /**
     * Удаляет пользователя по id.
     *
     * @param id идентификатор пользователя
     * @return пользователя,
     * возвращает ошибку NotFoundInBdException, если пользователь не найден
     */
    Optional<ShelterClients> deleteById(Integer id);

    /**
     * Удаляет всех пользователей.
     */
    void deleteAllUsers();
}