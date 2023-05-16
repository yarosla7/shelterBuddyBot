package com.devpro.shelterBuddyBot.repository.dao;

import com.devpro.shelterBuddyBot.model.ShelterBuddy;
import com.devpro.shelterBuddyBot.model.entity.ShelterClients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShelterClientsDao extends JpaRepository<ShelterClients, Integer> {

    Optional<ShelterClients> findByShelterBuddyAndNumberLike(ShelterBuddy shelter, String number);

    Optional<ShelterClients> findByChatIdAndTookAnimalAndShelterBuddy(Integer chatId, Boolean tookAnimal, ShelterBuddy shelterBuddy);

    Optional<ShelterClients> findByChatIdAndShelterBuddy (Integer chatId, ShelterBuddy shelterBuddy);

}
