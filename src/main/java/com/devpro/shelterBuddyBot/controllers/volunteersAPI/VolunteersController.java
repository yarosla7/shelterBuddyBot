package com.devpro.shelterBuddyBot.controllers.volunteersAPI;

import com.devpro.shelterBuddyBot.model.Volunteer;
import com.devpro.shelterBuddyBot.service.VolunteersService;
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
@RequestMapping("/volunteers")
@Tag(name = "Контроллер для работы с базой данных волонтеров",
        description = """
                Здесь можно добавлять, удалять волонтеров в базе данных, а так же получить список всех волонтеров
                """)
public class VolunteersController {
    private final VolunteersService volunteersService;

    public VolunteersController(VolunteersService volunteersService) {
        this.volunteersService = volunteersService;
    }

    @Operation(summary = "Получить список всех волонтеров",
            description = "Здесь можно получить список всех волонтеров, список будет сформирован в файле типа JSON")
    @ApiResponse(responseCode = "200", description = "Получен список всех волонтеров")
    @GetMapping("/findAll")
    @ResponseBody
    public List<Volunteer> findAll() {
        return volunteersService.findAll();
    }

    @Operation(summary = "Получить волонтера по его ID",
            description = "Выдаст волонтера с указанным ID, если такого волонтера нет - выдаст ошибку 404")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Волонтер найден.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "Волонтер с указанным ID не найден.")
            }
    )
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Volunteer> findById(@PathVariable Long id) {
        Optional volunteer = volunteersService.findById(id);
        if (volunteer.isPresent()) {
            return ResponseEntity.ok((Volunteer) volunteer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Добавить волонтера в Базу Данных",
            description = "Добавит волонтера в БД с присвоенным уникальным id. Поле Id можно не трогать (присвоится автоматически)")
    @ApiResponse(
            responseCode = "200",
            description = "Волонтер добавлен.",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
                    )
            }
    )
    @PostMapping("/")
    @ResponseBody
    public void addVolunteer(@RequestBody Volunteer volunteer) {
        volunteersService.addVolunteer(volunteer);
    }

    @Operation(summary = "Удалить волонтера по его ID",
            description = "Удалить волонтера с указанным ID, если такого волонтера нет - выдаст ошибку 404")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Волонтер удален.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "Волонтер с указанным ID не найден.")
            }
    )
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (volunteersService.findById(id).isPresent()) {
            volunteersService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}