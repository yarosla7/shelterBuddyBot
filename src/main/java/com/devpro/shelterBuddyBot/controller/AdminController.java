package com.devpro.shelterBuddyBot.controller;

import com.devpro.shelterBuddyBot.model.entity.Reports;
import com.devpro.shelterBuddyBot.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    @Operation(summary = "Присвоение животного усыновителю")
    public ResponseEntity<String> adoptAnimal(@RequestParam Integer animalId,
                                              @RequestParam Integer userId) {
        try {
            adminService.adoptAnimal(animalId, userId);
            return ResponseEntity.ok("Животное присвоено усыновителю");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/show-reports")
    @Operation(summary = "Посмотреть отчеты")
    public List<Reports> showAllReports() {
        return adminService.showAllReports();
    }

    @PostMapping("/add-volunteer")
    @Operation(summary = "Добавить волонтера")
    public ResponseEntity<String> addVolunteer(@RequestParam Long chatId,
                                               @RequestParam String name) {
        adminService.addVolunteer(chatId, name);
        return ResponseEntity.ok("Волонтер добавлен");
    }
}
