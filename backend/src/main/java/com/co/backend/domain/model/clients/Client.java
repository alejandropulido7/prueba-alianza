package com.co.backend.domain.model.clients;

import com.co.backend.application.exceptions.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Client {
    private Long id;
    private String sharedKey;
    private String businessId;
    private String email;
    private String phone;
    private LocalDate dataAdded;
}
