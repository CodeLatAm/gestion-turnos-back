package com.getion.turnos.exception;

public class BusinessHourAlreadyInUseException extends RuntimeException {

    public BusinessHourAlreadyInUseException(String message){
        super(message);
    }
}
