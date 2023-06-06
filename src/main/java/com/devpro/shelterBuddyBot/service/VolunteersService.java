package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.model.Volunteer;

import java.util.List;
import java.util.Optional;

public interface VolunteersService {
    /**
     * Возвращает список всех волонтеров.
     *
     * @return список волонтеров.
     */

    List<Volunteer> findAll();

    /**
     * Возвращает волонтера по его идентификатору.
     *
     * @param id идентификатор волонтера.
     * @return волонтер с заданным идентификатором.
     */

    Optional<Volunteer> findById(Long id);

    /**
     * Добавляет нового волонтера.
     *
     * @param volunteer добавляемый волонтер.
     */
    Volunteer addVolunteer(Volunteer volunteer);

    /**
     * Удаляет волонтера по его идентификатору.
     *
     * @param id идентификатор волонтера, которого необходимо удалить.
     * @return волонтер с заданным идентификатором.
     */

    Optional<Volunteer> deleteById(Long id);
}
