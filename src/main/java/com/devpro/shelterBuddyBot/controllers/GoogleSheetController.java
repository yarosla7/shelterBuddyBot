package com.devpro.shelterBuddyBot.controllers;


import com.devpro.shelterBuddyBot.model.util.ClientDataReport;
import com.devpro.shelterBuddyBot.service.impl.GoogleApiServiceIml;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;


@RestController
@RequestMapping("/google-sheet")
@RequiredArgsConstructor
public class GoogleSheetController {

    private final GoogleApiServiceIml googleApiServiceIml;


    @GetMapping("/get-data")
    public List<ClientDataReport> readDataFromGoogleSheet() throws GeneralSecurityException, IOException {
        return googleApiServiceIml.readDataFromGoogleSheet();
    }
}
