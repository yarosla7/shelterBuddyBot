package com.devpro.shelterBuddyBot.exeption;

public class NotFoundInBdException extends RuntimeException{
    public NotFoundInBdException(String message) {

        super(message);
    }
}
