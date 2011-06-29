package com.bee32.sem.chance;

import java.io.IOException;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.module.SEMModuleServer;

@Using(SEMChanceUnit.class)
public class SEMChanceServer
        extends SEMModuleServer {

    public static void main(String[] args)
            throws IOException {
        new SEMChanceServer().startAndWait(args);
    }

}
