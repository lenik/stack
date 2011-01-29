package com.bee32.plover.restful.oid;

import java.util.Arrays;

public class VectorChecksum {

    public static int get(int[] vector) {
        return Arrays.hashCode(vector);
    }

    public static int get(String path) {
        return -1;
    }

}
