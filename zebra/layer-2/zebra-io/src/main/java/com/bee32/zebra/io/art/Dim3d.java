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

    public boolean isZero() {
        return dx == 0.0 && dy == 0.0 && dz == 0.0;
    }

    public double getVolume() {
        return dx * dy * dz;
    }

    public String format(String delim) {
        return dx + delim + dy + delim + dz;
    }

    @Override
    public String toString() {
        return format(", ");
    }

}
