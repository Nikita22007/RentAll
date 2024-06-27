package ru.tinkoff.rentall.exception;

import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import ru.tinkoff.rentall.dto.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@ControllerAdvice
public class ExceptionHandler1 {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDTO> badRequestException(BadRequestException ex){
        ErrorDTO errorDTO = new ErrorDTO(400, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorDTO> internalServerErrorException(InternalServerErrorException ex){
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }

}
