package com.getion.turnos.exception;

public class UserNotAuthenticatedException extends RuntimeException{

    public UserNotAuthenticatedException(String message){
        super(message);
    }
}
