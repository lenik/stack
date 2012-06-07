package com.bee32.plover.site.cfg;

import org.junit.Assert;

public class SamplesSelectionTest
        extends Assert {

    public static void main(String[] args) {
        for (SamplesSelection ss : SamplesSelection.values()) {
            System.out.println(ss.getLabel());
        }
    }

}
