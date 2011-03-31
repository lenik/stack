package com.bee32.plover.test;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;

import com.bee32.plover.arch.ISupportLibrary;

public class AssembledTestCase<T>
        extends UnitTestCase<T> {

    private Set<ISupportLibrary> libraries;

    public AssembledTestCase() {
        libraries = new LinkedHashSet<ISupportLibrary>();
    }

    protected void install(ISupportLibrary library) {
        libraries.add(library);
    }

    @Before
    public void setUpLibraries()
            throws Exception {
        for (ISupportLibrary library : libraries)
            library.startup();
    }

    @After
    public void tearDownLibraries()
            throws Exception {
        for (ISupportLibrary library : libraries)
            library.shutdown();
    }

}
