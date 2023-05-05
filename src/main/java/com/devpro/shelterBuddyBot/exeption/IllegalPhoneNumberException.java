package com.devpro.shelterBuddyBot.exeption;

public class IllegalPhoneNumberException extends RuntimeException {
    public IllegalPhoneNumberException(String message) {
        super(message);
    }
}
