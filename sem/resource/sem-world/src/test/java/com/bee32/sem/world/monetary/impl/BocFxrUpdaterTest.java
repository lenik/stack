package com.bee32.sem.world.monetary.impl;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.sem.world.monetary.FxrTable;

public class BocFxrUpdaterTest
        extends Assert {

    static BocFxrUpdater updater = new BocFxrUpdater();

    @Test
    public void testDownload()
            throws IOException {
    }

    public static void main(String[] args)
            throws IOException {
        FxrTable table = updater.download();
        System.out.println(table.toString());

    }

}
