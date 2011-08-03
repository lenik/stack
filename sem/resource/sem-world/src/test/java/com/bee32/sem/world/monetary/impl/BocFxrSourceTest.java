package com.bee32.sem.world.monetary.impl;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.sem.world.monetary.FxrTable;

public class BocFxrSourceTest
        extends Assert {

    @Test
    public void testDownload()
            throws IOException {
        FxrSamplesSource src = new FxrSamplesSource();
        src.download();
    }

    public static void main(String[] args)
            throws IOException {
        FxrSamplesSource src = new FxrSamplesSource();
        for (int i = 0; i < FxrSamplesSource.FILES.length; i++) {
            FxrTable tab = src.download();
            System.out.println("Table " + i + ":");
            System.out.println(tab);
            System.out.println();
        }
    }

}
