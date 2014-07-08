package com.bee32.zebra.oa.model.hr;

import org.junit.Assert;

import net.bodz.bas.io.ITreeOut;
import net.bodz.bas.io.Stdio;
import net.bodz.bas.io.impl.TreeOutImpl;

public class JobPositionTest
        extends Assert {

    public static void main(String[] args) {
        ITreeOut out = TreeOutImpl.from(Stdio.cout);
        for (JobPositionGroup g : JobPosition.getGroups()) {
            out.println(g);
            out.enter();
            for (JobPosition item : g.getItems()) {
                out.println(item);
            }
            out.leave();
        }
    }

}
