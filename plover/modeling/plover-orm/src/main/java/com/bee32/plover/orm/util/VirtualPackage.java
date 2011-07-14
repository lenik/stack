package com.bee32.plover.orm.util;

import java.util.HashMap;
import java.util.Map;

public class VirtualPackage
        extends SamplesPackage {

    static final String STANDARD_NAME = "std";
    static final String NORMAL_NAME = "normal";
    static final String WORSE_NAME = "worse";
    static final String EVERYTHING_NAME = "everything";

    public VirtualPackage() {
        super();
    }

    public VirtualPackage(String name) {
        super(name);
    }

    @Override
    public boolean isVirtual() {
        return true;
    }

    static final Map<String, VirtualPackage> virtualPackages;

    public static final VirtualPackage STANDARD;
    public static final VirtualPackage NORMAL;
    public static final VirtualPackage WORSE;
    public static final VirtualPackage EVERYTHING;

    static {
        virtualPackages = new HashMap<String, VirtualPackage>();
        STANDARD = createDiamond(STANDARD_NAME, null);
        NORMAL = createDiamond(NORMAL_NAME, STANDARD);
        WORSE = createDiamond(WORSE_NAME, NORMAL);
        EVERYTHING = createDiamond(EVERYTHING_NAME, WORSE);
    }

    static VirtualPackage createDiamond(String name, VirtualPackage dependOn) {
        VirtualPackage startPackage = new VirtualPackage(name + ":start");
        VirtualPackage endPackage = new VirtualPackage(name + ":end");

        endPackage.dependencies.add(startPackage);
        startPackage.dependencies.add(dependOn);

        virtualPackages.put(name, endPackage);
        return endPackage;
    }

    public static Map<String, VirtualPackage> getVirtualPackages() {
        return virtualPackages;
    }

}
