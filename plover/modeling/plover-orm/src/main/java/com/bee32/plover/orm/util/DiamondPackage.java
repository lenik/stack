package com.bee32.plover.orm.util;

public class DiamondPackage
        extends SamplePackage {

    final DiamondPackage minPeer;

    public DiamondPackage(String name, SamplePackage parent) {
        super(name + ":max");
        minPeer = new DiamondPackage(name + ":min");
        if (parent != null)
            minPeer.addDependency(parent);
        this.addDependency(minPeer);
    }

    DiamondPackage(String peerName) {
        super(peerName);
        minPeer = null;
    }

    @Override
    public boolean isVirtual() {
        return true;
    }

    public void insert(SamplePackage pack) {
        if (minPeer != null)
            pack.addDependency(minPeer);
        DiamondPackage max = this;
        max.addDependency(pack);
    }

    public static class StandardGroup
            extends DiamondPackage {
        public StandardGroup() {
            super("std", null);
        }
    }

    public static class NormalGroup
            extends DiamondPackage {
        public NormalGroup() {
            super("normal", null); // std
        }
    }

    public static class WorseGroup
            extends DiamondPackage {
        public WorseGroup() {
            super("world", null);
        }
    }

    public static class EverythingGroup
            extends DiamondPackage {
        public EverythingGroup() {
            super("everything", null);
        }
    }

}
