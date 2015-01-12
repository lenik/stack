package com.bee32.zebra.tk.sql;

import java.util.Random;

import org.junit.Assert;

import com.bee32.zebra.tk.util.PrevNext;

import user.TestEnv;

public class FnMapperTest
        extends Assert {

    static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args)
            throws Exception {
        FnMapper mapper = TestEnv.getMapper(FnMapper.class);
        if (mapper == null)
            throw new NullPointerException("mapper");

        PrevNext prevNext = mapper.prevNext("public", "user", 100);
        System.out.println("prev: " + prevNext.getPrev());
        System.out.println("next: " + prevNext.getNext());
    }

}