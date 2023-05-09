package com.devpro.shelterBuddyBot.repository.rep;

import com.devpro.shelterBuddyBot.model.shelter.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    Optional<Cat> findByName(String name);
    Boolean existsByName(String name);
}
