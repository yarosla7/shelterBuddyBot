package com.devpro.shelterBuddyBot.dao;

import com.devpro.shelterBuddyBot.model.AnimalAdvice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalAdviceDao extends JpaRepository<AnimalAdvice, Integer> {
}
