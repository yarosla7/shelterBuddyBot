package com.devpro.shelterBuddyBot.service.impl;

import com.devpro.shelterBuddyBot.exeption.AnimalIsReservedException;
import com.devpro.shelterBuddyBot.exeption.NotFoundInBdException;
import com.devpro.shelterBuddyBot.exeption.ValidationException;
import com.devpro.shelterBuddyBot.model.User;
import com.devpro.shelterBuddyBot.model.enity.MessageToVolunteer;
import com.devpro.shelterBuddyBot.model.enity.StatusAnimal;
import com.devpro.shelterBuddyBot.model.shelter.Dog;
import com.devpro.shelterBuddyBot.repository.rep.DogRepository;
import com.devpro.shelterBuddyBot.service.DogService;
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
public class DogServiceImpl implements DogService {

    private final DogRepository dogRepository;
    private final ValidationService validationService;
    private final UserService userService;
    private final MessageToVolunteerService messageToVolunteerService;

    @Override
    public Dog createDog(Dog dog) {
        if(!validationService.validate(dog)) {
            throw new ValidationException(dog.toString());
        }
        return dogRepository.save(dog);
    }

    @Override
    public Dog findById(Long id) {
        Optional<Dog> dog = dogRepository.findById(id);
        if (dog.isPresent()) {
            return dog.get();
        } else {
            throw new NotFoundInBdException("Не найдено в базе данных");
        }
    }

    @Override
    public Dog updateById(Long id, Dog dog) {
        if (dogRepository.findById(id).isPresent()){
            dog.setId(id);
            return dogRepository.save(dog);
        } else {
            throw new NotFoundInBdException("Не найдено в базе данных");
        }
    }

    @Override
    public Dog deleteById(Long id) {
        Dog dog = findById(id);
        dogRepository.delete(dog);
        return dog;
    }

    @Override
    public List<Dog> findAll() {
        return dogRepository.findAll();
    }

    /**
     * Вывод полного списка животных, находящихся в приюте из БД.
     * Используется метод репозитория {@link DogRepository#findAll}
     *
     * @return
     */
    @Override
    public List<Dog> findAllInShelter(){
        return findAll().stream()
                .filter(x -> x.getStatusAnimal().equals(StatusAnimal.IN_THE_SHELTER))
                .collect(Collectors.toList());
    }

    @Override
    public Dog findByName(String name) { return dogRepository.findByName(name).get();
    }

    /**
     * Вывод списка собак по статусу
     * @param statusAnimal
     * @return
     */
    @Override
    public List<Dog> showAllByStatus(StatusAnimal statusAnimal) {
        return findAll().stream()
                .filter(x -> x.getStatusAnimal().equals(statusAnimal))
                .collect(Collectors.toList());
    }

    /**
     * Бронирование собаки на 24 часа
     * @param name
     * @param phone
     * @return
     */
    //404, 405, 409
    @Override
    public Dog reserveDog(String name, String phone) {
        User user;
        if (dogRepository.existsByName(name)) {
            if (userService.findByPhone(phone) == null) {
                user = new User(phone);
                userService.createUser(user);
            } else {
                user = userService.findByPhone(phone);
            }
            Dog dog = findByName(name);
            if (dog.getStatusAnimal().equals(StatusAnimal.RESERVED)) {
                throw new AnimalIsReservedException("Собака уже забронирована");
            }
            dog.setStatusAnimal(StatusAnimal.RESERVED);
            MessageToVolunteer message = new MessageToVolunteer(user.getPhoneNumber(), "забронировал собаку " + dog.getName());
            messageToVolunteerService.createMessageToVolunteer(message);
            return createDog(dog);
        } else {
            throw new NotFoundInBdException("Нет собаки с таком именем");
        }
    }

    /**
     * Замена статуса животного
     * @param name
     * @param statusAnimal
     * @return
     */
    @Override
    public Dog changeStatusAnimal(String name, StatusAnimal statusAnimal) {
        Dog dog = findByName(name);
        dog.setStatusAnimal(statusAnimal);
        return createDog(dog);
    }
}
