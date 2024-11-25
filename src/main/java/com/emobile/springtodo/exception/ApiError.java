package com.emobile.springtodo.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Описание ошибок во время работы приложения")
public class ApiError {

    @Schema(description = "Подробный список ошибок, возникших при работе",
            example = "Поле не может быть длиннее 50 символов")
    private List<String> message;

    @Schema(description = "Описание ошибки", example = "Ошибка валидации")
    private String status;

    @Schema(description = "Время возникновения ошибки")
    private LocalDateTime timestamp;
}
