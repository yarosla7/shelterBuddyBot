package com.devpro.shelterBuddyBot.repository.rep;

import com.devpro.shelterBuddyBot.model.enity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findBySenderAndReceiveDate(Long sender, LocalDate localDate);
}
