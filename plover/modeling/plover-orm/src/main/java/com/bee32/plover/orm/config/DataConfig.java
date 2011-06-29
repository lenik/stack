package com.bee32.plover.orm.config;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.free.LocalFileResource;
import javax.free.SystemProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataConfig {

    static Logger logger = LoggerFactory.getLogger(DataConfig.class);

    public static final String DATA_DIR;
    static {
        String userHome = SystemProperties.getUserHome();
        if (userHome == null) {
            userHome = System.getenv("HOME");
            if (userHome == null)
                userHome = ".";
        }
        DATA_DIR = userHome + "/.data";
        new File(DATA_DIR).mkdirs();
    }

    public static final Properties getProperties(String configName) {

        logger.info("Loading data config: " + configName);

        File configFile = new File(DATA_DIR, configName + ".properties");
        Properties properties;

        if (!configFile.exists()) {
            logger.warn("File isn't existed: " + configFile + ", using empty settings.");
            properties = new Properties();

        } else {
            logger.info("Load properties: " + configFile);

            try {
                properties = new LocalFileResource(configFile).forLoad().loadProperties();
            } catch (IOException e) {
                logger.error("Failed to load " + configFile, e);
                throw new RuntimeException("Failed to load " + configFile, e);
            }
        }

        properties.put("config.file", configFile);
        return properties;
    }

}
