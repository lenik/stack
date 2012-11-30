package com.bee32.sem.track;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Assert;

import com.bee32.sem.track.entity.Issue;

public class BundlePathTest
        extends Assert {

    public static void main(String[] args)
            throws ClassNotFoundException {

        Class<?> clazz = Issue.class;
        String name = clazz.getName();
        String replace = name.replace('.', '/');
        ResourceBundle bundle = ResourceBundle.getBundle(replace, Locale.CHINA);
        String string = bundle.getString("label");
        System.out.println(string);
    }
}
