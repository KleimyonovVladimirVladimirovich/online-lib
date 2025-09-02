package com.kleim.online_library.error;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ServerErrorDTO> handleValidationException(
            MethodArgumentNotValidException e
    ) {
        logger.error("Got exception", e);

        String detailedMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(it -> it.getField() + ": " + it.getDefaultMessage())
                .collect(Collectors.joining(", "));

        var errorDTO =new ServerErrorDTO("Ошибка валидации запроса",
                detailedMessage,
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);

    }
    @ExceptionHandler
    public ResponseEntity<ServerErrorDTO> handleGenericException(
            Exception e
    ) {
        logger.error("Got exception", e);
        var errorDTO =new ServerErrorDTO("Ошибка на сервере",
                e.getMessage(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }

    @ExceptionHandler
    public ResponseEntity<ServerErrorDTO> handleNotFoundException(
            NoSuchElementException e
    ) {
        logger.error("No such element", e);

        var errorDTO =new ServerErrorDTO("Entity not found",
                e.getMessage(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }
    @ExceptionHandler
    public ResponseEntity<ServerErrorDTO> handleEntityNotFoundException(
            EntityNotFoundException e
    ) {
        logger.error("No such element", e);

        var errorDTO =new ServerErrorDTO("Entity not found",
                e.getMessage(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }
}
