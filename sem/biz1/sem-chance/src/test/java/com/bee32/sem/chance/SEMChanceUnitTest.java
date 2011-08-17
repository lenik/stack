package com.bee32.sem.chance;

import org.junit.Assert;

import com.bee32.plover.orm.unit.PUnitDumper;

public class SEMChanceUnitTest
        extends Assert {

    public static void main(String[] args) {
        SEMChanceUnit unit = new SEMChanceUnit();
        System.out.println(PUnitDumper.dump(unit));
    }

}
