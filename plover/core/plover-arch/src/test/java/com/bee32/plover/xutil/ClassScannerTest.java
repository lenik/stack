package com.bee32.plover.xutil;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class ClassScannerTest
        extends Assert {

    ClassScanner scanner = new ClassScanner();

    public ClassScannerTest()
            throws IOException {
        scanner.scan("com.bee32");
        scanner.scan("user");
    }

    @Test
    public void testDumpAnnotations() {
        scanner.dump();
    }

}
