package com.bee32.plover.ajax;

import com.google.gson.Gson;

class Foo<T> {
    T val;
}

class Bar
        extends Foo<Integer> {
}

public class JsonUtilTest {

    public static void main(String[] args) {
        Bar bar = new Bar();
        bar.val = 123;

        Gson gson = new Gson();
        System.out.println(gson.toJson(bar));
    }

}
