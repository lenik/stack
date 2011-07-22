package com.bee32.sem.world.math;

import java.util.Map;
import java.util.TreeMap;

/**
 * 直线插值。
 */
public class InterpolatedMap
        extends TreeMap<Integer, Double> {

    private static final long serialVersionUID = 1L;

    public double getInterpolated(int x) {
        Map.Entry<Integer, Double> A = floorEntry(x);
        Map.Entry<Integer, Double> B = ceilingEntry(x);

        if (A == null && B == null)
            return Double.NaN;

        int a, b;
        double ya, yb;

        if (A == null) {
            return B.getValue();
        } else {
            a = A.getKey();
            ya = A.getValue();
        }

        if (B == null) {
            return A.getValue();
        } else {
            b = B.getKey();
            yb = B.getValue();
        }

        assert a <= b;
        if (b - a == 0)
            return ya;

        double y = ya + ((yb - ya) / (b - a)) * (x - a);
        return y;
    }

}
