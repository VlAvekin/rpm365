package com.aviekinvlad.rpm365api.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * @author Vlad Aviekin
 * @date 16.02.2025
 */

@Service
@RequiredArgsConstructor
public class DefaultMessageService implements MessageService {

    private final MessageSource messageSource;

    @Override
    public String massage(String message) {
        return messageSource.getMessage(message, null, Locale.getDefault());
    }

    @Override
    public String massage(String message, Object... args) {
        return messageSource.getMessage(message, args, Locale.getDefault());
    }
}
