package com.bee32.icsf.principal.util;

import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.PrincipalCheckException;
import com.bee32.icsf.principal.PrincipalDiag;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.builtins.MultiPrincipal;

public class PrincipalDiagTest {

    public PrincipalDiagTest() {
        PrincipalDiag.maxRefDepth = 4;
        Locale.setDefault(Locale.ENGLISH); // exception message used.
    }

    @Test
    public void testNoLoop1()
            throws PrincipalCheckException {
        User u1 = new User("u1", "User 1");
        PrincipalDiag.checkDeadLoop(u1);
    }

    @Test
    public void testNoLoop2_UG()
            throws PrincipalCheckException {
        User u1 = new User("u1", "User 1");
        Group g1 = new Group("g1", "Group 1");
        u1.addAssignedGroup(g1);
        PrincipalDiag.checkDeadLoop(u1);
    }

    @Test(expected = PrincipalCheckException.class)
    public void testLoop2_MM()
            throws Exception {
        MultiPrincipal m1 = new MultiPrincipal("m1");
        m1.addInheritance(m1);
        PrincipalDiag.checkDeadLoop(m1);
    }

    @Test(expected = PrincipalCheckException.class)
    public void testLoop3_MMM()
            throws Exception {
        MultiPrincipal m1 = new MultiPrincipal("m1");
        MultiPrincipal m2 = new MultiPrincipal("m2");
        m1.addInheritance(m2);
        m2.addInheritance(m1);
        PrincipalDiag.checkDeadLoop(m1);
    }

    @Test(expected = PrincipalCheckException.class)
    public void testLoop4_MMMM()
            throws Exception {
        MultiPrincipal m1 = new MultiPrincipal("m1");
        MultiPrincipal m2 = new MultiPrincipal("m2");
        MultiPrincipal m3 = new MultiPrincipal("m3");
        m1.addInheritance(m2);
        m2.addInheritance(m3);
        m3.addInheritance(m1);
        PrincipalDiag.checkDeadLoop(m1);
    }

    @Test(expected = PrincipalCheckException.class)
    public void testLoopOver5_MMMMM()
            throws Exception {
        MultiPrincipal m1 = new MultiPrincipal("m1");
        MultiPrincipal m2 = new MultiPrincipal("m2");
        MultiPrincipal m3 = new MultiPrincipal("m3");
        MultiPrincipal m4 = new MultiPrincipal("m4");
        MultiPrincipal m5 = new MultiPrincipal("m5");
        m1.addInheritance(m2);
        m2.addInheritance(m3);
        m3.addInheritance(m4);
        m4.addInheritance(m5);
        try {
            PrincipalDiag.checkDeadLoop(m1);
        } catch (PrincipalCheckException e) {
            assertTrue("Should be max-depth failure", e.getMessage().contains("deep"));
            throw e;
        }
    }

}
