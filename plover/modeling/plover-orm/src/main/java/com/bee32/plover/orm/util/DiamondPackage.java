package com.bee32.plover.orm.util;

import java.util.HashMap;
import java.util.Map;

public class DiamondPackage
        extends SamplePackage {

    DiamondPackage start;

    public DiamondPackage() {
        super();
    }

    public DiamondPackage(String name) {
        super(name);
    }

    @Override
    public boolean isVirtual() {
        return true;
    }

    public DiamondPackage getStart() {
        return start;
    }

    public void setStart(DiamondPackage start) {
        this.start = start;
    }

    static final Map<String, DiamondPackage> virtualPackages;

    static final String STANDARD_NAME = "std";
    static final String NORMAL_NAME = "normal";
    static final String WORSE_NAME = "worse";
    static final String EVERYTHING_NAME = "everything";

    public static final DiamondPackage STANDARD;
    public static final DiamondPackage NORMAL;
    public static final DiamondPackage WORSE;
    public static final DiamondPackage EVERYTHING;

    static {
        virtualPackages = new HashMap<String, DiamondPackage>();
        STANDARD = createDiamond(STANDARD_NAME, null);
        NORMAL = createDiamond(NORMAL_NAME, STANDARD);
        WORSE = createDiamond(WORSE_NAME, NORMAL);
        EVERYTHING = createDiamond(EVERYTHING_NAME, WORSE);
    }

    static DiamondPackage createDiamond(String name, DiamondPackage dependOn) {
        DiamondPackage startPackage = new DiamondPackage(name + ":start");
        DiamondPackage endPackage = new DiamondPackage(name + ":end");

        if (dependOn != null)
            startPackage.dependencies.add(dependOn);
        endPackage.start = startPackage;

        virtualPackages.put(name, endPackage);
        return endPackage;
    }

    public static Map<String, DiamondPackage> getVirtualPackages() {
        return virtualPackages;
    }

}
