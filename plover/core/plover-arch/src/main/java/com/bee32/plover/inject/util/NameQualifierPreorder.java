package com.bee32.plover.inject.util;

import javax.free.IPreorder;
import javax.free.PackageNamePreorder;

public class NameQualifierPreorder
        extends QualifierPreorder<String> {

    private final IPreorder<String> proxy = PackageNamePreorder.getInstance();

    @Override
    public int compare(String a, String b) {
        return proxy.compare(a, b);
    }

    @Override
    public String getPreceding(String qualifier) {
        return proxy.getPreceding(qualifier);
    }

    @Override
    public int precompare(String a, String b) {
        return proxy.precompare(a, b);
    }

    private static final NameQualifierPreorder instance = new NameQualifierPreorder();

    public static NameQualifierPreorder getInstance() {
        return instance;
    }

}
