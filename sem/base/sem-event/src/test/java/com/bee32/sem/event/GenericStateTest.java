package com.bee32.sem.event;

import org.junit.Assert;
import org.junit.Test;

public class GenericStateTest
        extends Assert {

    @Test
    public void test() {
    }

    public static void main(String[] args) {
        for (EventState val : GenericState.values()) {
            System.out.println(val);
        }
    }

}
