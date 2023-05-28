package com.devpro.shelterBuddyBot.service.impl;

import com.devpro.shelterBuddyBot.model.ShelterClients;
import com.devpro.shelterBuddyBot.model.Volunteer;
import com.devpro.shelterBuddyBot.repository.dao.VolunteerDao;
import com.devpro.shelterBuddyBot.service.VolunteersService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class VolunteersServiceImpl implements VolunteersService {

    private final VolunteerDao volunteerDao;

    public VolunteersServiceImpl(VolunteerDao volunteerDao) {
        this.volunteerDao = volunteerDao;
    }

    @Override
    public List<Volunteer> findAll() {
        return volunteerDao.findAll();
    }

    @Override
    public Optional<Volunteer> findById(Long id) {
        return volunteerDao.findById(id);
    }

    @Override
    public Volunteer addVolunteer(Volunteer volunteer) {
       return volunteerDao.save(volunteer);
    }

    @Override
    public Optional<Volunteer> deleteById(Long id) {
        Optional<Volunteer> temp = volunteerDao.findById(id);
        volunteerDao.deleteById(id);
        return temp;
    }
}
