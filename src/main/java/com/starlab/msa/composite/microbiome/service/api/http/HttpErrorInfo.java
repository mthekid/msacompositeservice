package com.starlab.msa.composite.microbiome.service.api.http;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class HttpErrorInfo {

    private final ZonedDateTime timestamp;
    private final String path;
    private final HttpStatus httpStatus;
    private final String message;

    public HttpErrorInfo() {
        timestamp = null;
        this.httpStatus = null;
        this.path = null;
        this.message = null;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }

    public int getHttpStatus() {
        return httpStatus.value();
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return httpStatus.getReasonPhrase();
    }

    public HttpErrorInfo(String path, HttpStatus httpStatus, String message) {
        this.timestamp = ZonedDateTime.now();
        this.path = path;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}