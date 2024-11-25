package com.emobile.springtodo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "Сущность задачи")
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    @Schema(description = "Идентификатор задачи", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    @Schema(description = "Название задачи", example = "Задача")
    private String title;

    @Schema(description = "Описание задачи", example = "Описание задачи")
    private String description;

}
