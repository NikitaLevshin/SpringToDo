package com.emobile.springtodo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "Сущность для обновления задачи")
@AllArgsConstructor
public class UpdateTaskDto {

    @Schema(description = "Название задачи", example = "Задача")
    @Size(min = 1, max = 50, message = "Task size should be less than 50 symbols")
    private String title;

    @Schema(description = "Описание задачи", example = "Описание задачи")
    @Size(max = 500, message = "Task description should be less than 500 symbols")
    private String description;

}