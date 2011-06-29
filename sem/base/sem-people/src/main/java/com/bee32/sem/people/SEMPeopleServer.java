package com.bee32.sem.people;

import java.io.IOException;

import com.bee32.sem.test.SEMUnitServer;

public class SEMPeopleServer
        extends SEMUnitServer {

    public static void main(String[] args)
            throws IOException {
        new SEMPeopleServer().startAndWait(args);
    }

}
