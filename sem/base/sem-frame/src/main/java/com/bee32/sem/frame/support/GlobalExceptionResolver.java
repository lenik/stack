package com.bee32.sem.frame.support;

import java.util.Properties;

import javax.inject.Singleton;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Component
/* jsr-330 scope not work here */@Singleton
@Lazy
public class GlobalExceptionResolver
        extends SimpleMappingExceptionResolver {

    static final Properties globalMappings = new Properties();

    /**
     * @since spring-mvc 3.0.5
     */
    public GlobalExceptionResolver() {
        this.setExceptionMappings(globalMappings);
    }

    public static void register(Class<? extends Exception> exception, String viewId) {
        globalMappings.put(exception.getName(), viewId);
    }

}
