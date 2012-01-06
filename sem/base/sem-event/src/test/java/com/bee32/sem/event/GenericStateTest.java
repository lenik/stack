package com.bee32.sem.event;

import org.junit.Test;

import com.bee32.plover.arch.util.EnumAltTestCase;

public class GenericStateTest
        extends EnumAltTestCase {

    @Test
    public void test() {
    }

    public static void main(String[] args) {
        for (EventState<?> state : GenericState.values()) {
            System.out.println(state);
        }
    }

}
