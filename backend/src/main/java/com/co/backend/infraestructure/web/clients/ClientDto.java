package com.co.backend.infraestructure.web.clients;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ClientDto {
    @NotNull(message = "field name cannot be null")
    private String name;
    @NotNull(message = "field phone cannot be null")
    private String phone;
    @NotNull(message = "field email cannot be null")
    private String email;
    @NotNull(message = "field startDate cannot be null")
    private LocalDate startDate;
    @NotNull(message = "field endDate cannot be null")
    private LocalDate endDate;
}
