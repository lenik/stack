package com.bee32.plover.arch.builtin;

import com.bee32.plover.arch.credit.Contributor;

public class Lenik
        extends Contributor {

    static final double LENIK_RANK = -Math.exp(Math.PI);

    private Lenik() {
    }

    @Override
    public double getRank() {
        return LENIK_RANK;
    }

    @Override
    public String getEmail() {
        return "xjl@99jsj.com";
    }

    @Override
    public String getOrganization() {
        return "99 TVU/ZJ";
    }

    @Override
    public String getRole() {
        return "System Architect";
    }

    private static final Lenik lenik = new Lenik();

    public static Lenik getLenik() {
        return lenik;
    }

}
