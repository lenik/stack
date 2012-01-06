package com.bee32.sem.uber;

import java.io.IOException;

import com.bee32.plover.xutil.ClassScanner;

public class SEMUberClasses {

    public static ClassScanner scanner;

    static {
        scanner = new ClassScanner();
        try {
            scanner.scan("com.bee32");
            scanner.scan("user");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void main(String[] args)
            throws Exception {
        System.out.printf("Size: %d/%d\n" + scanner.size2(), scanner.size());
    }

}
