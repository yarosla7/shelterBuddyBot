package com.devpro.shelterBuddyBot.service.impl;

import com.devpro.shelterBuddyBot.exeption.AnimalIsReservedException;
import com.devpro.shelterBuddyBot.exeption.NotFoundInBdException;
import com.devpro.shelterBuddyBot.exeption.ValidationException;
import com.devpro.shelterBuddyBot.model.User;
import com.devpro.shelterBuddyBot.model.enity.MessageToVolunteer;
import com.devpro.shelterBuddyBot.model.enity.StatusAnimal;
import com.devpro.shelterBuddyBot.model.shelter.Cat;
import com.devpro.shelterBuddyBot.repository.rep.CatRepository;
import com.devpro.shelterBuddyBot.service.CatService;
import com.devpro.shelterBuddyBot.service.MessageToVolunteerService;
import com.devpro.shelterBuddyBot.service.UserService;
import com.devpro.shelterBuddyBot.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatServiceImpl implements CatService {
    private final CatRepository catRepository;
    private final ValidationService validationService;
    private final UserService userService;
    private final MessageToVolunteerService messageToVolunteerService;


    @Override
    public Cat createCat(Cat cat) {
        if (!validationService.validate(cat)) {
            throw new ValidationException(cat.toString());
        }
        return catRepository.save(cat);
    }


    @Override
    public Cat findById(Long id) {
        Optional<Cat> cat = catRepository.findById(id);
        if (cat.isPresent()) {
            return cat.get();
        } else {
            throw new NotFoundInBdException("Не найдено в базе данных");
        }
    }


    @Override
    public Cat updateById(Long id, Cat cat) {
        if (catRepository.findById(id).isPresent()) {
            cat.setId(id);
            return catRepository.save(cat);
        } else {
            throw new NotFoundInBdException("Не найдено в базе данных");
        }
    }


    @Override
    public Cat deleteById(Long id) {
        Cat cat = findById(id);
        catRepository.delete(cat);
        return cat;
    }


    @Override
    public List<Cat> findAll() {
        return catRepository.findAll();
    }

    /**
     * Вывод полного списка животных, находящихся в приюте из БД.
     * Используется метод репозитория {@link CatRepository#findAll}
     *
     * @return
     */
    @Override
    public List<Cat> findAllInShelter(){
        return findAll().stream()
                .filter(x -> x.getStatusAnimal().equals(StatusAnimal.IN_THE_SHELTER))
                .collect(Collectors.toList());
    }

    @Override
    public Cat findByName(String name) {
        return catRepository.findByName(name).get();
    }

    /**
     * Вывод списка котов по статусу
     * @param statusAnimal
     * @return
     */
    @Override
    public List<Cat> showAllByStatus(StatusAnimal statusAnimal) {
        return findAll().stream()
                .filter(x -> x.getStatusAnimal().equals(statusAnimal))
                .collect(Collectors.toList());
    }

    /**
     * Бронирование кота на 24 часа
     * @param name
     * @param phone
     * @return
     */
    //404, 405, 409
    @Override
    public Cat reserveCat(String name, String phone) {
        User user;
        if (catRepository.existsByName(name)) {
            if (userService.findByPhone(phone) == null) {
                user = new User(phone);
                userService.createUser(user);
            } else {
                user = userService.findByPhone(phone);
            }
            Cat cat = findByName(name);
            if (cat.getStatusAnimal().equals(StatusAnimal.RESERVED)) {
                throw new AnimalIsReservedException("Кошка уже забронирована");
            }
            cat.setStatusAnimal(StatusAnimal.RESERVED);
            MessageToVolunteer message = new MessageToVolunteer(user.getPhoneNumber(), user.getPhoneNumber() + " забронировал кошку " + cat.getName());
            messageToVolunteerService.createMessageToVolunteer(message);
            return createCat(cat);
        } else {
            throw new NotFoundInBdException("Нет кота с таком именем");
        }
    }

    /**
     * Замена статуса животного
     * @param name
     * @param statusAnimal
     * @return
     */
    @Override
    public Cat changeStatusAnimal(String name, StatusAnimal statusAnimal) {
        Cat cat = findByName(name);
        cat.setStatusAnimal(statusAnimal);
        return createCat(cat);
    }
}
