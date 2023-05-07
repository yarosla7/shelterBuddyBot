package com.devpro.shelterBuddyBot.repository.dao;

import com.devpro.shelterBuddyBot.model.entity.ShelterClients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelterClientsDao extends JpaRepository<ShelterClients, Integer> {
}
