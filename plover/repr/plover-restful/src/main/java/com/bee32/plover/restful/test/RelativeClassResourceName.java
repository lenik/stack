package com.bee32.plover.restful.test;

import java.net.URL;

public class RelativeClassResourceName {

    public static void main(String[] args) {
        // java.lang.String -> java.lang.reflect.Field
        URL String_class = String.class.getResource("String.class");
        URL reflectField_class = String.class.getResource("reflect/Field.class");
        System.out.println(String_class);
        System.out.println(reflectField_class); // Got!
    }

}
