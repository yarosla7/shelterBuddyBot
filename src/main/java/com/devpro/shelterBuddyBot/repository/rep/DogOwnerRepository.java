package com.devpro.shelterBuddyBot.repository.rep;

import com.devpro.shelterBuddyBot.model.shelter.DogOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DogOwnerRepository extends JpaRepository<DogOwner, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<DogOwner> findByPhoneNumber(String number);
}
