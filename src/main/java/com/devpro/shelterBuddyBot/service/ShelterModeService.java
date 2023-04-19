package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.service.impl.HashMapShelterModeServiceImpl;
import com.devpro.shelterBuddyBot.util.ShelterType;

public interface ShelterModeService {
    static ShelterModeService getInstance() {
        return new HashMapShelterModeServiceImpl();
    }

    // получаем приют с ключом chatId
    ShelterType getShelter(long chatId);


    // задаем чатID и тип(енам) приюта
    void setShelter(long chatId, ShelterType shelter);
}
