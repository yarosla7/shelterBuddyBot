package com.devpro.shelterBuddyBot.controllers.volunteersAPI;

import com.devpro.shelterBuddyBot.model.Animal;
import com.devpro.shelterBuddyBot.service.AnimalService;
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
@RequestMapping("/animals")
@Tag(name = "Контроллер для работы с базой данных животных",
        description = """
                Здесь можно добавлять, удалять, редактировать животных в базе данных, а так же получить список всех животных
                """)
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @Operation(summary = "Получить список всех животных",
            description = "Здесь можно получить список всех животных, список будет сформирован в файле типа JSON")
    @ApiResponse(responseCode = "200", description = "Получен список всех животных")
    @GetMapping("/findAll")
    @ResponseBody
    public List<Animal> findAll() {
        return animalService.findAll();
    }

    @Operation(summary = "Получить животное по его ID",
            description = "Выдаст животное с указанным ID, если такого животного нет - выдаст ошибку 404")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Животное найдено.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Animal.class))
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "Животное с указанным ID не найдено.")
            }
    )
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Animal> findById(@PathVariable Integer id) {
        Optional animal = animalService.findById(id);
        if (animal.isPresent()) {
            return ResponseEntity.ok((Animal) animal.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Добавить животное в Базу Данных",
            description = "Добавит животное в БД с присвоенным уникальным id. Поле Id можно не трогать (присвоится автоматически)")
    @ApiResponse(
            responseCode = "200",
            description = "Животное добавлено.",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Animal.class))
                    )
            }
    )
    @PostMapping("/")
    @ResponseBody
    public void addAnimal(@RequestBody Animal animal) {
        animalService.addAnimal(animal);
    }

    @Operation(summary = "Изменить данные животного в Базе Данных",
            description = "Изменяеть данные о животном")
    @ApiResponse(
            responseCode = "200",
            description = "Данные успешно изменены.",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Animal.class))
                    )
            }
    )
    @PutMapping("/")
    @ResponseBody
    public void editAnimal(@RequestBody Animal animal) {
        animalService.editAnimal(animal);
    }

    @Operation(summary = "Удалить животное по его ID",
            description = "Удалить животное с указанным ID, если такого животного нет - выдаст ошибку 404")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Животное удалено.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Animal.class))
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "Животное с указанным ID не найдено.")
            }
    )
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        if (animalService.findById(id).isPresent()) {
            animalService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}