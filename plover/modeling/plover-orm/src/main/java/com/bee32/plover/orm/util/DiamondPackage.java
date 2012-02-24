package com.bee32.plover.orm.util;

import java.util.HashMap;
import java.util.Map;

public class DiamondPackage
        extends SamplePackage {

    final DiamondPackage base;

    public DiamondPackage(String name, DiamondPackage base) {
        // super(name);
        this.base = base;
    }

    @Override
    public boolean isVirtual() {
        return true;
    }

    public void insert(SamplePackage pack) {
        addDependency(pack);
        if (base != null)
            pack.addDependency(base);
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

    static DiamondPackage createDiamond(String name, DiamondPackage parent) {
        DiamondPackage base = new DiamondPackage(name + ":start", parent);
        DiamondPackage diamond = new DiamondPackage(name + ":end", base);

        if (parent != null)
            base.addDependency(parent);

        if (base != null)
            diamond.addDependency(base);

        virtualPackages.put(name, diamond);
        return diamond;
    }

    public static Map<String, DiamondPackage> getVirtualPackages() {
        return virtualPackages;
    }

}
