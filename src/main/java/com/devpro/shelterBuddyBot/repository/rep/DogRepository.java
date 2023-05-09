package com.devpro.shelterBuddyBot.repository.rep;

import com.devpro.shelterBuddyBot.model.shelter.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DogRepository extends JpaRepository<Dog,Long> {

    Optional<Dog> findByName(String name);
    boolean existsByName(String name);
}
