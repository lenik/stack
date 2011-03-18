package com.bee32.plover.orm.test;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.free.LocalFileResource;
import javax.free.SystemProperties;

public class DataConfig {

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

        File propertiesFile = new File(DATA_DIR, configName + ".properties");

        if (!propertiesFile.exists())
            return new Properties();

        try {
            return new LocalFileResource(propertiesFile).forLoad().loadProperties();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + propertiesFile, e);
        }
    }

}
