package com.aviekinvlad.rpm365api.service.message;

/**
 * @author Vlad Aviekin
 * @date 16.02.2025
 */

public interface MessageService {

    String massage(String message);

    String massage(String message, Object... args);
}
