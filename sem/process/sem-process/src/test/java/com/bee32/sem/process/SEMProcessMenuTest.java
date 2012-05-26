package com.bee32.sem.process;

import org.junit.Assert;

public class SEMProcessMenuTest
        extends Assert {

    public static void main(String[] args) {
        SEMProcessMenu pm = new SEMProcessMenu();
        pm.assembleOnce();
        String displayName = pm.level.getAppearance().getDisplayName();
        System.out.println("name = " + displayName);
    }

}
