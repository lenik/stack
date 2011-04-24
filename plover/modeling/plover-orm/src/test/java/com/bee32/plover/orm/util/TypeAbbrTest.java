package com.bee32.plover.orm.util;

import org.junit.Assert;
import org.junit.Test;

public class TypeAbbrTest
        extends Assert
        implements ITypeAbbrAware {

    @Test
    public void test() {

    }

    public static void main(String[] args) {
        String eventStateAbbr = ABBR.abbr("EventState");
        System.out.println(eventStateAbbr);
    }

}
