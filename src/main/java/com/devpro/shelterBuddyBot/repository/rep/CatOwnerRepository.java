package com.devpro.shelterBuddyBot.repository.rep;

import com.devpro.shelterBuddyBot.model.shelter.CatOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface CatOwnerRepository extends JpaRepository<CatOwner, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<CatOwner> findByPhoneNumber(String number);
}
