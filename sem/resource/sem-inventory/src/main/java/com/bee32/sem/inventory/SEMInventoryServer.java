package com.bee32.sem.inventory;

import java.io.IOException;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.module.SEMModuleServer;

@Using(SEMInventoryUnit.class)
public class SEMInventoryServer
        extends SEMModuleServer {

    public static void main(String[] args)
            throws IOException {
        new SEMInventoryServer().startAndWait();
    }

}
