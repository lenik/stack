package com.bee32.plover.inject.cref;

public class ContextRefs {

    public static final ContextRef AUTO = new AutoContext();

    public static final ContextRef SCAN_STD = new ScanStdContext(AUTO);
    public static final ContextRef SCAN_STDX = new ScanStdxContext(AUTO);

    public static final ContextRef SCAN_TEST = new ScanTestContext(AUTO);
    public static final ContextRef SCAN_TESTX = new ScanTestxContext(AUTO);

}
