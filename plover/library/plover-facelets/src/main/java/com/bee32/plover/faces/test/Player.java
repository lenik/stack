package com.bee32.plover.faces.test;

public class Player
        extends FaceletsTestCase {

    public Player() {
        contextPath = "";
    }

    @Override
    protected int getRefreshPeriod() {
        return 1000;
    }

    public static void main(String[] args)
            throws Exception {
        new Player().browseAndWait("version.jsf");
    }

}
