package com.devpro.shelterBuddyBot.repository.rep;

import com.devpro.shelterBuddyBot.model.enity.TrialPeriod;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrialPeriodRepository extends JpaRepository<TrialPeriod, Long> {
}
