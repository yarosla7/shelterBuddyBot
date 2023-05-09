package com.devpro.shelterBuddyBot.service.impl;

import com.devpro.shelterBuddyBot.exeption.NotFoundInBdException;
import com.devpro.shelterBuddyBot.model.enity.Report;
import com.devpro.shelterBuddyBot.repository.rep.ReportRepository;
import com.devpro.shelterBuddyBot.service.ReportService;
import com.devpro.shelterBuddyBot.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ValidationService validationService;

    @Override
    public Report createReport(Report report) {
        /*if (!validationService.validate(report)) {
            throw new ValidationException(report.toString());
        }*/
        return reportRepository.save(report);
    }
    @Override
    public Report findById(Long id) {
        Optional<Report> report = reportRepository.findById(id);
        if (report.isPresent()) {
            return report.get();
        } else {
            throw new NotFoundInBdException("Не найдено в базе данных");
        }
    }
    @Override
    public Report updateById(Long id, Report report) {
        if (reportRepository.findById(id).isPresent()) {
            report.setId(id);
            return reportRepository.save(report);
        } else {
            throw new NotFoundInBdException("Не найдено в базе данных");
        }
    }
    @Override
    public Report deleteById(Long id) {
        Report report = findById(id);
        reportRepository.delete(report);
        return report;
    }
    @Override
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    @Override
    public Report findBySenderAndDate(Long sender, LocalDate localDate){
        Optional<Report> report = reportRepository.findBySenderAndReceiveDate(sender, localDate);
        if (report.isPresent()){
            return report.get();
        } else {
            return null;
        }
    }

}
