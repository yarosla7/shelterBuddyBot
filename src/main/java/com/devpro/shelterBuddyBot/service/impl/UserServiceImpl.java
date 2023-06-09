package com.devpro.shelterBuddyBot.service.impl;

import com.devpro.shelterBuddyBot.model.ShelterClients;
import com.devpro.shelterBuddyBot.repository.dao.ShelterClientsDao;
import com.devpro.shelterBuddyBot.service.UserService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class UserServiceImpl implements UserService {

    private final ShelterClientsDao shelterClientsDao;

    public UserServiceImpl(ShelterClientsDao shelterClientsDao) {
        this.shelterClientsDao = shelterClientsDao;
    }

    @Override
    public List<ShelterClients> findAll() {
        return shelterClientsDao.findAll();
    }

    @Override
    public Optional<ShelterClients> findById(Integer id) {
        return shelterClientsDao.findById(id);
    }


    @Override
    public ShelterClients editUser(ShelterClients user) {
            return shelterClientsDao.save(user);
    }

    @Override
    public Optional<ShelterClients> deleteById(Integer id) {
        Optional<ShelterClients> temp = shelterClientsDao.findById(id);
        shelterClientsDao.deleteById(id);
        return temp;
    }

    @Override
    public void deleteAllUsers() {
        shelterClientsDao.deleteAll();
    }
}