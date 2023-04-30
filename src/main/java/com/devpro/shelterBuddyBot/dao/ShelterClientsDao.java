package com.devpro.shelterBuddyBot.dao;

import com.devpro.shelterBuddyBot.model.ShelterClients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelterClientsDao extends JpaRepository<ShelterClients, Integer> {

}