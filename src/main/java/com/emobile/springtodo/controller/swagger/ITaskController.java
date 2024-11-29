package com.emobile.springtodo.controller.swagger;

import com.emobile.springtodo.dto.NewTaskDto;
import com.emobile.springtodo.dto.TaskDto;
import com.emobile.springtodo.dto.UpdateTaskDto;
import com.emobile.springtodo.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Tag(name = "Задачи", description = "Работа с задачами")
public interface ITaskController {

    @Operation(
            summary = "Создание задачи",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Задача создана",
                        content = @Content(schema = @Schema(implementation = TaskDto.class))

                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Ошибка валидации при создании задачи",
                        content = @Content(schema = @Schema(implementation = ApiError.class))
                )
            }
    )
    TaskDto createTask(@RequestBody @Valid NewTaskDto newTaskDto);

    @Operation(
            summary = "Получение задачи по идентификатору",
            responses = {
                    @ApiResponse(
                    responseCode = "404",
                    description = "Задача не найдена",
                    content = @Content(
                        examples = @ExampleObject(
                                                    value = """
                                                            {
                                                                "message": "Задача не найдена",
                                                                "status": 404,
                                                                "timestamp": "2024-11-12T15:27:26.878Z"
                                                            }
                                                            """
                        )
                    )
                    )
            }
    )
    TaskDto getTask(@Parameter(description = "Идентификатор задачи", required = true) int id);

    @Operation(
            summary = "Получение списка всех задач"
    )
    List<TaskDto> getTasks(@Parameter(description = "Лимит на количество выводимых задач")
                           @Min(1) int size,
                           @Parameter(description = "Сколько задач пропустить перед выводом") int page);

    @Operation(
            summary = "Обновление задачи",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Задача обновлена",
                            content = @Content(schema = @Schema(implementation = TaskDto.class))

                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка валидации при создании задачи",
                            content = @Content(schema = @Schema(implementation = ApiError.class))
                    )
            }
    )
    TaskDto updateTask(@Parameter(description = "Идентификатор задачи", required = true) int id,
                       @RequestBody @Valid UpdateTaskDto updateTaskDto);

    @Operation(
            summary = "Удаление задачи",
            responses = {
                    @ApiResponse(
                            responseCode = "404",
                            description = "Задача не найдена",
                            content = @Content(
                                    examples = @ExampleObject(
                                            value = """
                                                            {
                                                                "message": "Задача не найдена",
                                                                "status": 404,
                                                                "timestamp": "2024-11-12T15:27:26.878Z"
                                                            }
                                                            """
                                    )
                            )
                    )
            }
    )
    void deleteTask(@Parameter(description = "Идентификатор задачи", required = true) int id);
}
