package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.model.Animal;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для работы волонтеров с базой данных животных
 */
public interface AnimalService {
    /**
     * Возвращает список всех животных.
     *
     * @return список животных.
     */

    List<Animal> findAll();

    /**
     * Возвращает животное по его идентификатору.
     *
     * @param id идентификатор животного.
     * @return животное с заданным идентификатором.
     */

    Optional<Animal> findById(Integer id);

    /**
     * Добавляет новое животное.
     *
     * @param animal добавляемое животное.
     */
    Animal addAnimal(Animal animal);

    /**
     * Изменяет данные о животном.
     *
     * @param animal измененные данные о животном.
     * @return животное с заданным идентификатором.
     */

    Animal editAnimal(Animal animal);

    /**
     * Удаляет животное по его идентификатору.
     *
     * @param id идентификатор животного, которое необходимо удалить.
     * @return животное с заданным идентификатором.
     */

    Optional<Animal> deleteById(Integer id);
}