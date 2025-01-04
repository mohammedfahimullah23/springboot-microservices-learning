package com.example.Study.Hall.Management.exception;

// i think this is not required since spring framework by default hanfles if u add in global class
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
