package com.devpro.shelterBuddyBot.service;

import com.devpro.shelterBuddyBot.model.entity.ClientDataReport;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface GoogleApiService {

    List<ClientDataReport> readDataFromGoogleSheet() throws GeneralSecurityException, IOException;

    void saveToDataBase() throws GeneralSecurityException, IOException;


}
