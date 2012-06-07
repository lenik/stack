package com.bee32.plover.site.cfg;

import static org.junit.Assert.fail;

import org.junit.Test;

public class DBDialectTest {

    @Test
    public void test() {
        fail("Not yet implemented");
    }

    public static void main(String[] args) {
        for (DBDialect d : DBDialect.values()) {
            System.out.println("Dialect: " + d.getName());
        }

        DBDialect psql = DBDialect.forName("postgresql");
        System.out.println("PSQL = " + psql);
    }

}
