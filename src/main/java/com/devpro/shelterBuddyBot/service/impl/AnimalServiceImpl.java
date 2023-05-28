package com.devpro.shelterBuddyBot.service.impl;

import com.devpro.shelterBuddyBot.model.Animal;
import com.devpro.shelterBuddyBot.repository.dao.AnimalDao;
import com.devpro.shelterBuddyBot.service.AnimalService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class AnimalServiceImpl implements AnimalService {
    private final AnimalDao animalDao;

    public AnimalServiceImpl(AnimalDao animalDao) {
        this.animalDao = animalDao;
    }

    @Override
    public List<Animal> findAll() {
        return animalDao.findAll();
    }

    @Override
    public Optional<Animal> findById(Integer id) {
        return animalDao.findById(id);
    }

    @Override
    public Animal addAnimal(Animal animal) {
        return animalDao.save(animal);
    }

    @Override
    public Animal editAnimal(Animal animal) {
        return animalDao.save(animal);
    }

    @Override
    public Optional<Animal> deleteById(Integer id) {
        Optional<Animal> temp = animalDao.findById(id);
        animalDao.deleteById(id);
        return temp;
    }
}
