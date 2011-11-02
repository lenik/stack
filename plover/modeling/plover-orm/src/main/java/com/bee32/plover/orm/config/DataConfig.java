package com.bee32.plover.orm.config;

import java.io.File;

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

}
