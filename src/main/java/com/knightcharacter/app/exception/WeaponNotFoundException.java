package com.knightcharacter.app.exception;

public class WeaponNotFoundException extends RuntimeException {

    public WeaponNotFoundException(String message) {
        super(message);
    }
}
