package com.co.backend.infraestructure.adapters.r2dbc.clients;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("clients")
public class ClientsDao {

    @Id
    private Long id;
    private String shared_key;
    private String business_id;
    private String email;
    private String phone;
    private LocalDate start_date;
    private LocalDate end_date;
}
