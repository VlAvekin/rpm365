package com.aviekinvlad.rpm365api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;

import java.util.Objects;

/**
 * @author Vlad Aviekin
 * @date 13.01.2025
 */

@Component
public class TestMassage {

    @Autowired
    private Environment env;

    public String massage(String error) {
        return env.getProperty(error);
    }

    public String massage(String error, Object ...args) {
        String format = Objects.requireNonNull(env.getProperty(error));
        return String.format(format, args);
    }

}
