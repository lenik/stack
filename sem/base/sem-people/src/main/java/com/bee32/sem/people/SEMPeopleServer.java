package com.bee32.sem.people;

import java.io.IOException;

import com.bee32.sem.module.SEMModuleServer;

public class SEMPeopleServer
        extends SEMModuleServer {

    public static void main(String[] args)
            throws IOException {
        new SEMPeopleServer().startAndWait(args);
    }

}
