package com.devpro.shelterBuddyBot.controllers.volunteersAPI;

import com.devpro.shelterBuddyBot.model.ShelterClients;
import com.devpro.shelterBuddyBot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Data
@Controller
@RequestMapping("/users")
@Tag(name = "Контроллер для работы с базой данных пользователей",
        description = """
                Здесь можно редактировать пользователей в базе данных,
                а так же получить список всех пользователей или удалить всех сразу.
                """)
public class UsersController {
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Получить список всех пользователей",
            description = "Здесь можно получить список всех пользователей, список будет сформирован в файле типа JSON")
    @ApiResponse(responseCode = "200", description = "Получен список всех пользователей")
    @GetMapping("/findAll/")
    @ResponseBody
    public List<ShelterClients> findAll() {
        return userService.findAll();
    }

    @Operation(summary = "Получить пользователя по его ID",
            description = "Выдаст пользователя с указанным ID, если такого пользователя нет - выдаст ошибку 404")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь найден.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ShelterClients.class))
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "Пользователь с указанным ID не найден.")
            }
    )
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ShelterClients> findById(@PathVariable Integer id) {
        Optional user = userService.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok((ShelterClients) user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Изменить данные пользователя в Базе Данных",
            description = "Изменяет данные о пользователе")
    @ApiResponse(
            responseCode = "200",
            description = "Данные успешно изменены.",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ShelterClients.class))
                    )
            }
    )
    @PutMapping("/")
    @ResponseBody
    public void editAnimal(@RequestBody ShelterClients shelterClients) {
        userService.editUser(shelterClients);
    }

    @Operation(summary = "Удалить пользователя по его ID",
            description = "Удалить пользователя с указанным ID(внутренний id из БД), если такого пользователя нет - выдаст ошибку 404")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь удален.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ShelterClients.class))
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "Пользователь с указанным ID не найден.")
            }
    )
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Удалить список всех пользователей",
            description = "Здесь можно удалить список всех пользователей")
    @ApiResponse(responseCode = "200", description = "Пользователи успешно удалены")
    @GetMapping("/deleteAll")
    @ResponseBody
    public ResponseEntity<Void> deleteAllUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.ok().build();
    }
}