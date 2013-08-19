package com.bee32.sem.chance;

import org.junit.Assert;

import com.bee32.plover.orm.unit.PUnitDumper;

/**
 * SEM 机会单元 测试
 *
 * <p lang="en">
 * SEM Chance Unti Test
 */
public class SEMChanceUnitTest
        extends Assert {

    public static void main(String[] args) {
        SEMChanceUnit unit = new SEMChanceUnit();
        System.out.println(PUnitDumper.dump(unit));
    }

}
