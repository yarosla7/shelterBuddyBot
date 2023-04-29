package com.devpro.shelterBuddyBot.service.impl;

import com.devpro.shelterBuddyBot.service.ShelterModeService;
import com.devpro.shelterBuddyBot.util.ShelterType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

//  мапа служит сейчас заглушкой, позже их надо будет переносить в бд
@Service
public class HashMapShelterModeServiceImpl implements ShelterModeService {

    private final Map<Long, ShelterType> shelter = new HashMap<>();


    // получаем приют с ключом chatId
    @Override
    public ShelterType getShelter(long chatId) {
        return shelter.get(chatId);
    }


    // задаем чатID и тип(енам) приюта
    @Override
    public void setShelter(long chatId, ShelterType shelter) {
        this.shelter.put(chatId, shelter);
    }
}