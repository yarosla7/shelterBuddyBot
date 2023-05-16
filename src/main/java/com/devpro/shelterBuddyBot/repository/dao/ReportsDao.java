package com.devpro.shelterBuddyBot.repository.dao;

import com.devpro.shelterBuddyBot.model.entity.Reports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportsDao extends JpaRepository<Reports, Integer> {


    List<Reports> findAllByIsReportOkIsNull();


    @Query(value = "select * from reports r where id in (select max(r.id) id  from reports r group by r.user_id)",
            nativeQuery = true)
    List<Reports> findLastReport();


}
