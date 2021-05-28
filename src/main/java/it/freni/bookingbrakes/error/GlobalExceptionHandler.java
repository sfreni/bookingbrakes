package it.freni.bookingbrakes.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
    public class GlobalExceptionHandler {
        @ExceptionHandler(IdAlreadyExists.class)
        public ResponseEntity<ErrorDto> generateAlreadyExistException(IdAlreadyExists ex) {
            ErrorDto errorDTO = new ErrorDto();
            errorDTO.setMessage(ex.getMessage());
            errorDTO.setStatus(String.valueOf(ex.getStatus().value()));
            errorDTO.setTime(new Date().toString());

            return new ResponseEntity<>(errorDTO, ex.getStatus());
        }

    @ExceptionHandler(NotObjectFound.class)
    public ResponseEntity<ErrorDto> generateNotFoundException(NotObjectFound ex) {
        ErrorDto errorDTO = new ErrorDto();
        errorDTO.setMessage(ex.getMessage());
        errorDTO.setStatus(String.valueOf(ex.getStatus().value()));
        errorDTO.setTime(new Date().toString());

        return new ResponseEntity<>(errorDTO, ex.getStatus());
    }

    }

