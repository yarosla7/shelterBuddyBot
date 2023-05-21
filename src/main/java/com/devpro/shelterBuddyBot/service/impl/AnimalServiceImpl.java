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
    public void addAnimal(Animal animal) {
        animalDao.save(animal);
    }

    @Override
    public void editAnimal(Animal animal) {
        animalDao.save(animal);
    }

    @Override
    public void deleteById(Integer id) {
        animalDao.deleteById(id);
    }
}
