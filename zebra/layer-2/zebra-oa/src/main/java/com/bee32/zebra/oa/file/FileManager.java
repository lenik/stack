package com.bee32.zebra.oa.file;

import java.io.File;

import net.bodz.bas.c.system.SystemProperties;

public class FileManager {

    public String startPrefix = "files/";
    public String incomingPrefix = startPrefix + "incoming/";

    public File startDir;
    public File incomingDir;

    public FileManager() {
        File homeDir = new File(SystemProperties.getUserHome());
        startDir = new File(homeDir, startPrefix);
        startDir.mkdirs();
        incomingDir = new File(homeDir, incomingPrefix);
        incomingDir.mkdirs();
    }

    static FileManager instance = new FileManager();

    public static FileManager getInstance() {
        return instance;
    }

}
