package com.devpro.shelterBuddyBot.exeption;

public class ValidationException extends RuntimeException{
    public ValidationException(String entity) {
        super("Ошибка валидации сущности " + entity);
    }
}
