package com.devpro.shelterBuddyBot.dao;

import com.devpro.shelterBuddyBot.model.ShelterBuddy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelterDao extends JpaRepository<ShelterBuddy, Integer> {
}
