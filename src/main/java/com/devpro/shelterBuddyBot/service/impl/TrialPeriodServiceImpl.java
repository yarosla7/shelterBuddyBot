package com.devpro.shelterBuddyBot.service.impl;

import com.devpro.shelterBuddyBot.exeption.NotFoundInBdException;
import com.devpro.shelterBuddyBot.exeption.ValidationException;
import com.devpro.shelterBuddyBot.model.enity.TrialPeriod;
import com.devpro.shelterBuddyBot.repository.rep.TrialPeriodRepository;
import com.devpro.shelterBuddyBot.service.TrialPeriodService;
import com.devpro.shelterBuddyBot.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrialPeriodServiceImpl implements TrialPeriodService {
    private final TrialPeriodRepository trialPeriodRepository;
    private final ValidationService validationService;

    @Override
    public TrialPeriod createTrialPeriod(TrialPeriod trialPeriod) {
        if (!validationService.validate(trialPeriod)) {
            throw new ValidationException(trialPeriod.toString());
        }

        return trialPeriodRepository.save(trialPeriod);
    }

    @Override
    public TrialPeriod findById(Long id) {
        Optional<TrialPeriod> trialPeriod = trialPeriodRepository.findById(id);
        if (trialPeriod.isPresent()) {
            return trialPeriod.get();
        } else {
            throw new NotFoundInBdException("Не найдено в базе данных");
        }
    }

    @Override
    public TrialPeriod updateById(Long id, TrialPeriod trialPeriod) {
        if (trialPeriodRepository.findById(id).isPresent()){
            trialPeriod.setId(id);
            return trialPeriodRepository.save(trialPeriod);
        } else {
            throw new NotFoundInBdException("Не найдено в базе данных");
        }
    }

    @Override
    public TrialPeriod deleteById(Long id) {
        TrialPeriod trialPeriod = findById(id);
        trialPeriodRepository.delete(trialPeriod);
        return trialPeriod;
    }

    @Override
    public List<TrialPeriod> findAll() {
        return trialPeriodRepository.findAll();
    }

}
