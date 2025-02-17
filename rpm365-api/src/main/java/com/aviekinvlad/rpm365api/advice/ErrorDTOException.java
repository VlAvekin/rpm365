package com.aviekinvlad.rpm365api.advice;

/**
 * @author Vlad Aviekin
 * @date 13.01.2025
 */

public class ErrorDTOException extends Exception {
    public ErrorDTOException() {
    }
    public ErrorDTOException(String message) {
        super(message);
    }
}
