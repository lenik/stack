package com.bee32.ape.html.apex.beans.standalone;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

// @Component
public class ApexDbProperties
        extends PropertyPlaceholderConfigurer {

    public ApexDbProperties() {
        ClassPathResource propertiesResource = new ClassPathResource("db.properties");
        setLocation(propertiesResource);
        setIgnoreUnresolvablePlaceholders(true);
    }

}
