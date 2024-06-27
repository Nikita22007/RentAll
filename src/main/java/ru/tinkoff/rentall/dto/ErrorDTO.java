package ru.tinkoff.rentall.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorDTO {
    private String message;
    private int status;

    public ErrorDTO(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
