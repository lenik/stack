package com.bee32.zebra.oa.hr;

import org.junit.Assert;

import net.bodz.bas.io.ITreeOut;
import net.bodz.bas.io.Stdio;
import net.bodz.bas.io.impl.TreeOutImpl;

import com.bee32.zebra.oa.hr.JobPosition;
import com.bee32.zebra.oa.hr.JobPositionGroup;

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
