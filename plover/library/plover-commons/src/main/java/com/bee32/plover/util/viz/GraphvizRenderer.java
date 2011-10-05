package com.bee32.plover.util.viz;

public enum GraphvizRenderer {

    DOT,

    NEATO,

    TWOPI,

    CIRCO,

    FDP,

    ;

    public String getExecutable() {
        String name = name();
        String exec = name.toLowerCase();
        return exec;
    }

}
