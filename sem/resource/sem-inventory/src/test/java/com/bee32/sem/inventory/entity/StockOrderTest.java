package com.bee32.sem.inventory.entity;

import org.junit.Assert;

import com.bee32.sem.inventory.SEMInventorySamples;

public class StockOrderTest
        extends Assert {

    public static void main(String[] args) {
        String dump = SEMInventorySamples.takeInOrder1.dump();
        System.out.println(dump);
    }

}
