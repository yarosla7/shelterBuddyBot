package com.devpro.shelterBuddyBot.repository.dao;

import com.devpro.shelterBuddyBot.model.AnimalAdvice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalAdviceDao extends JpaRepository<AnimalAdvice, Integer> {
}
