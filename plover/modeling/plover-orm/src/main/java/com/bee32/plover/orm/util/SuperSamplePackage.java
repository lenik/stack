package com.bee32.plover.orm.util;

import com.bee32.plover.inject.NotAComponent;

@NotAComponent
public class SuperSamplePackage
        extends SamplePackage {

    final SuperSamplePackage minPeer;

    public SuperSamplePackage(String name, boolean maxSide) {
        super(name + (maxSide ? ":max" : ":min"));
        if (maxSide) {
            minPeer = new SuperSamplePackage(name, false);
            super.addDependency(minPeer);
        } else {
            minPeer = null;
        }
    }

    @Override
    public boolean isVirtual() {
        return true;
    }

    public void insert(SamplePackage pack) {
        if (minPeer != null)
            pack.addDependency(minPeer);
        SuperSamplePackage max = this;
        max.addDependency(pack);
    }

    public static class Standards
            extends SuperSamplePackage {
        public Standards() {
            super("standards", true);
        }
    }

    public static class Normals
            extends SuperSamplePackage {
        public Normals() {
            super("normals", true);
            addDependency(predefined(Standards.class));
        }
    }

    public static class Boundaries
            extends SuperSamplePackage {
        public Boundaries() {
            super("boundaries", true);
            addDependency(predefined(Normals.class));
        }
    }

    public static class Everythings
            extends SuperSamplePackage {
        public Everythings() {
            super("everythings", true);
            addDependency(predefined(Boundaries.class));
        }
    }

}
