package com.bee32.plover.collections;

import java.util.Arrays;

public class TestSort {

    public static void main(String[] args) {

        // String[] v = { "1-abc", "12-def", "2-xyz", };

        String[] v1 = { "1-", "11-", "1-a", "11-a", "2-", "21-", "2-a", "21-a", };
        String[] v = new String[v1.length];
        for (int i = 0; i < v1.length; i++)
            v[v1.length - i - 1] = v1[i];

        Arrays.sort(v);
        for (String s : v)
            System.out.println(s);
    }

}
