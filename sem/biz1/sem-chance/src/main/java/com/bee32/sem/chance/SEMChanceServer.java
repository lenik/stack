package com.bee32.sem.chance;

import java.io.IOException;

import com.bee32.sem.test.SEMUnitServer;

public class SEMChanceServer
        extends SEMUnitServer {

    public static void main(String[] args)
            throws IOException {
        new SEMChanceServer().startAndWait(args);
    }

}
