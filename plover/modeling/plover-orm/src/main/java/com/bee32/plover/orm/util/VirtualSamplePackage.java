package com.bee32.plover.orm.util;

import java.util.HashMap;
import java.util.Map;

public class VirtualSamplePackage
        extends SamplePackage {

    static final String STANDARD_NAME = "std";
    static final String NORMAL_NAME = "normal";
    static final String WORSE_NAME = "worse";
    static final String EVERYTHING_NAME = "everything";

    public VirtualSamplePackage() {
        super();
    }

    public VirtualSamplePackage(String name) {
        super(name);
    }

    @Override
    public boolean isVirtual() {
        return true;
    }

    static final Map<String, VirtualSamplePackage> virtualPackages;

    public static final VirtualSamplePackage STANDARD;
    public static final VirtualSamplePackage NORMAL;
    public static final VirtualSamplePackage WORSE;
    public static final VirtualSamplePackage EVERYTHING;

    static {
        virtualPackages = new HashMap<String, VirtualSamplePackage>();
        STANDARD = createDiamond(STANDARD_NAME, null);
        NORMAL = createDiamond(NORMAL_NAME, STANDARD);
        WORSE = createDiamond(WORSE_NAME, NORMAL);
        EVERYTHING = createDiamond(EVERYTHING_NAME, WORSE);
    }

    static VirtualSamplePackage createDiamond(String name, VirtualSamplePackage dependOn) {
        VirtualSamplePackage startPackage = new VirtualSamplePackage(name + ":start");
        VirtualSamplePackage endPackage = new VirtualSamplePackage(name + ":end");

        endPackage.dependencies.add(startPackage);
        startPackage.dependencies.add(dependOn);

        virtualPackages.put(name, endPackage);
        return endPackage;
    }

    public static Map<String, VirtualSamplePackage> getVirtualPackages() {
        return virtualPackages;
    }

}
