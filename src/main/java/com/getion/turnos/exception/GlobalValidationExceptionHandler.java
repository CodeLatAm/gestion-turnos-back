package com.getion.turnos.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalValidationExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String message = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(message, errorMessage);
            errors.put("error: ", HttpStatus.BAD_REQUEST.toString());
        });
        return errors;
    }

/*
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponse> validationException(MethodArgumentNotValidException ex){
        List<String> messages = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            messages.add(error.getDefaultMessage());
        });
        return ResponseEntity.badRequest()
                .body(new MessageResponse(HttpStatus.BAD_REQUEST, Operations.trimBrackets(messages.toString())));
    }
*/
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> userAlreadyExist(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ProfileNotFountException.class)
    public ResponseEntity<String> handleProfileNotFountException(ProfileNotFountException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<String> handleUserNotAuthenticatedException(UserNotAuthenticatedException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(HealthCenterAlreadyExistException.class)
    public ResponseEntity<String> handleHealthCenterAlreadyExistException(HealthCenterAlreadyExistException ex) {
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(HealthCenterNotFoundException.class)
    public ResponseEntity<String> handleHealthCenterNoFoundException(HealthCenterNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(BusinessHourAlreadyInUseException.class)
    public ResponseEntity<String> handleBusinessHourAlreadyInUseException(BusinessHourAlreadyInUseException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(PatientAlreadyExistExceptions.class)
    public ResponseEntity<String> handlePatientAlreadyExistExceptions(PatientAlreadyExistExceptions ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
