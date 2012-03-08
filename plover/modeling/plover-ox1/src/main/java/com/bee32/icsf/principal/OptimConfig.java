package com.bee32.icsf.principal;

public class OptimConfig {

    public static boolean setControlSide = false;

    /**
     * For ManyToMany assoc, if save/update parent and child at the same time, the collections in
     * each peer are saved, thus may get duplicated keys.
     *
     * So, in the samples, you should add to the collection in "raw" manner.
     */
    public static boolean setPeerSide = true;

}
