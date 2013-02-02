package com.bee32.ape.html.apex.beans;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class ApexUiProperties
        extends PropertyPlaceholderConfigurer {

    public ApexUiProperties() {
        ClassPathResource propertiesResource = new ClassPathResource("ui.properties");
        setLocation(propertiesResource);
        setIgnoreUnresolvablePlaceholders(true);
    }

}
