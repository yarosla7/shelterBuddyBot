package com.devpro.shelterBuddyBot.repository.dao;

import com.devpro.shelterBuddyBot.model.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnimalDao extends JpaRepository<Animal, Integer> {


    List<Animal> findAllByAdoptedIsFalseAndAdoptDate(LocalDate adoptDate);
}
