package com.devpro.shelterBuddyBot.service.impl;

import com.devpro.shelterBuddyBot.exeption.NotFoundInBdException;
import com.devpro.shelterBuddyBot.exeption.ValidationException;
import com.devpro.shelterBuddyBot.model.User;
import com.devpro.shelterBuddyBot.model.enity.*;
import com.devpro.shelterBuddyBot.model.shelter.Dog;
import com.devpro.shelterBuddyBot.model.shelter.DogOwner;
import com.devpro.shelterBuddyBot.repository.rep.DogOwnerRepository;
import com.devpro.shelterBuddyBot.service.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DogOwnerServiceImpl implements DogOwnerService {
    private final DogOwnerRepository dogOwnerRepository;
    private final ValidationService validationService;
    private final UserService userService;
    private final MessageToVolunteerService messageToVolunteerService;
    private final TrialPeriodService trialPeriodService;
    private final DogService dogService;

    @Override
    public DogOwner createDogOwner(DogOwner dogOwner) {
        if (!validationService.validate(dogOwner)) {
            throw new ValidationException(dogOwner.toString());
        }
        return dogOwnerRepository.save(dogOwner);
    }

    @Override
    public DogOwner findById(Long id) {
        Optional<DogOwner> dogOwner = dogOwnerRepository.findById(id);
        if (dogOwner.isPresent()) {
            return dogOwner.get();
        } else {
            throw new NotFoundInBdException("Не найдено в базе данных");
        }
    }

    @Override
    public DogOwner updateById(Long id, DogOwner dogOwner) {
        if (dogOwnerRepository.findById(id).isPresent()) {
            dogOwner.setId(id);
            return dogOwnerRepository.save(dogOwner);
        } else {
            throw new NotFoundInBdException("Не найдено в базе данных");
        }
    }

    @Override
    public DogOwner deleteById(Long id) {
        DogOwner dogOwner = findById(id);
        dogOwnerRepository.delete(dogOwner);
        return dogOwner;
    }

    @Override
    public List<DogOwner> findAll() {
        return dogOwnerRepository.findAll();
    }

    @Override
    public Boolean existsByPhoneNumber(String phoneNumber) {
        return dogOwnerRepository.existsByPhoneNumber(phoneNumber);
    }

    //TODO: 19.04.2023 Поправил метод по аналогии с методом в CatOwnerServiceImpl
    @Override
    public DogOwner findByPhoneNumber(String phoneNumber) {
//        if (existsByPhoneNumber(phoneNumber)){
//            return dogOwnerRepository.findByPhoneNumber(phoneNumber).get();
        Optional<DogOwner> dogOwner = dogOwnerRepository.findByPhoneNumber(phoneNumber);
        if (dogOwner.isPresent()) {
            return dogOwner.get();
        } else {
            return null;
        }
    }

    @Override
    public DogOwner getAnimalToTrialPeriod(String phoneNumber, String animalName, long trialDays) {
        if (!dogOwnerRepository.existsByPhoneNumber(phoneNumber)) {
            if (userService.findByPhone(phoneNumber) == null) {
                User user = userService.createUser(new User(phoneNumber));
                messageToVolunteerService.createMessageToVolunteer(new MessageToVolunteer(
                        phoneNumber, phoneNumber + " получил собаку " + animalName + " на испытательный срок в " + trialDays + " дней"
                ));
                return getDogOwner(phoneNumber, animalName, trialDays);
            } else {
                User user = userService.findByPhone(phoneNumber);
                messageToVolunteerService.createMessageToVolunteer(new MessageToVolunteer(
                        phoneNumber, phoneNumber + " получил собаку " + animalName + " на испытательный срок в " + trialDays + " дней"
                ));
                return getDogOwner(phoneNumber, animalName, trialDays);
            }
        } else {
            DogOwner dogOwner = findByPhoneNumber(phoneNumber);
            messageToVolunteerService.createMessageToVolunteer(new MessageToVolunteer(
                    phoneNumber, "получил кошку " + animalName + " на испытательный срок в " + trialDays + " дней"
            ));
            TrialPeriod currentTrialPeriod = new TrialPeriod(phoneNumber, animalName, LocalDate.now(), LocalDate.now().plusDays(trialDays),
                    new ArrayList<Report>(), TrialPeriodResult.IN_PROCESS);
            trialPeriodService.createTrialPeriod(currentTrialPeriod);
            Dog dog = dogService.findByName(animalName);
            dogService.changeStatusAnimal(animalName, StatusAnimal.TRIAL_PERIOD);
            dogService.createDog(dog);
            dogOwner.getTrialPeriodsInActiveStatus().add(currentTrialPeriod);
            dogOwner.getDogs().add(dog);
            return createDogOwner(dogOwner);
        }
    }

    private DogOwner getDogOwner(String phoneNumber, String animalName, long trialDays) {
        TrialPeriod currentTrialPeriod = new TrialPeriod(phoneNumber, animalName, LocalDate.now(), LocalDate.now().plusDays(trialDays),
                new ArrayList<Report>(), TrialPeriodResult.IN_PROCESS);
        trialPeriodService.createTrialPeriod(currentTrialPeriod);
        Dog dog = dogService.findByName(animalName);
        dogService.changeStatusAnimal(animalName, StatusAnimal.TRIAL_PERIOD);
        dogService.createDog(dog);
        DogOwner dogOwner = new DogOwner(phoneNumber, new ArrayList<Dog>(), new ArrayList<TrialPeriod>(), new ArrayList<TrialPeriod>());
        dogOwner.getTrialPeriodsInActiveStatus().add(currentTrialPeriod);
        dogOwner.getDogs().add(dog);
        return createDogOwner(dogOwner);
    }
}
