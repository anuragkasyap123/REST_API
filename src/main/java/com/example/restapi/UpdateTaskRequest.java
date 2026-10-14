package com.example.restapi;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateTaskRequest(
        @NotBlank(message = "must not be blank")
        @Size(max = 200, message = "must be at most 200 characters")
        String title,
        boolean completed) {
}
