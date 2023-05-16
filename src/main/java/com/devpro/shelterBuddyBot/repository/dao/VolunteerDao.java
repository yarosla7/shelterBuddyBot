package com.devpro.shelterBuddyBot.repository.dao;

import com.devpro.shelterBuddyBot.model.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VolunteerDao extends JpaRepository<Volunteer, Long> {

    Volunteer findByChatId(Long chatId);

    Optional<Volunteer> findFirstByChatIdNotNull();
}
