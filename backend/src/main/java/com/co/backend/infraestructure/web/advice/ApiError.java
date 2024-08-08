package com.co.backend.infraestructure.web.advice;

import lombok.Builder;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class ApiError {

    private String timestamp;
    private int status;
    private String error;

    public ApiError(int status, String error) {
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        this.status = status;
        this.error = error;
    }
}
