package com.devpro.shelterBuddyBot.repository.dao;

import com.devpro.shelterBuddyBot.model.util.ClientDataReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientDataReportDao extends JpaRepository<ClientDataReport, Integer> {

    Optional<ClientDataReport> findByIdReport(Integer id);

    List<ClientDataReport> findByProcessedFalseOrProcessedNull();
}
