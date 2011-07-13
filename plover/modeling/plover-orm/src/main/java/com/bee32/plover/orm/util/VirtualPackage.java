package com.bee32.plover.orm.util;

import java.util.HashMap;
import java.util.Map;

public class VirtualPackage
        extends SamplesPackage {

    public static final String STANDARD = "std";
    public static final String NORMAL = "normal";
    public static final String WORSE = "worse";
    public static final String EVERYTHING = "everything";

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
    static {
        virtualPackages = new HashMap<String, VirtualPackage>();

        VirtualPackage standardPackage = createDiamond(STANDARD, null);
        VirtualPackage normalPackage = createDiamond(NORMAL, standardPackage);
        VirtualPackage worsePackage = createDiamond(WORSE, normalPackage);
        createDiamond(EVERYTHING, worsePackage);
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
