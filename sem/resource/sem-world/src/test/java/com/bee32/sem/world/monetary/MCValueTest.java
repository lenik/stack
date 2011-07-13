package com.bee32.sem.world.monetary;

import static org.junit.Assert.fail;

import java.util.Locale;

import org.junit.Test;

public class MCValueTest {

    @Test
    public void test() {
        fail("Not yet implemented");
    }

    public static void main(String[] args) {
        Locale zh = Locale.forLanguageTag("zh-CN");
        Locale.setDefault(zh);
        MCValue mcv = new MCValue();
        System.out.println(mcv.getCurrency());
    }

}
