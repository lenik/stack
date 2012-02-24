package com.bee32.sem.inventory.entity;

import org.junit.Assert;

import com.bee32.sem.inventory.SEMInventorySamples;

public class StockOrderTest
        extends Assert {

    public static void main(String[] args) {
        StockOrder order1 = new SEMInventorySamples().takeInOrder1;
        String dump = order1.dump();
        System.out.println(dump);
    }

}
