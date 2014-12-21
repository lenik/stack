package com.bee32.zebra.io.art;

import java.io.Serializable;

public class Dim3d
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public double dx;
    public double dy;
    public double dz;

    public Dim3d() {
    }

    public Dim3d(double dx, double dy, double dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    public double getVolume() {
        return dx * dy * dz;
    }

    @Override
    public String toString() {
        return dx + " x " + dy + " x " + dz;
    }

}
