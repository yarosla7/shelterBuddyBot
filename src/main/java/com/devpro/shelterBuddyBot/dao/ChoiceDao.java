package com.devpro.shelterBuddyBot.dao;

import com.devpro.shelterBuddyBot.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceDao extends JpaRepository<Choice, Long> {
}
