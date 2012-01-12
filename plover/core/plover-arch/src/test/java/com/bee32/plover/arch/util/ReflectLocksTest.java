package com.bee32.plover.arch.util;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("unused")
public class ReflectLocksTest
        extends Assert {

    static class True
            implements ILockable {

        @Override
        public boolean isLocked() {
            return true;
        }

    }

    static class False
            implements ILockable {

        @Override
        public boolean isLocked() {
            return false;
        }

    }

    @Test
    public void testLocking1() {
        class C {
            True t = new True();
        }
        C obj = new C();
        assertTrue(ReflectLocks.isLocked(obj));
        obj.t = null;
        assertFalse(ReflectLocks.isLocked(obj));
    }

    @Test
    public void testLocking2() {
        class C {
            True t;
            False f;
        }
        C obj = new C(); // - -
        assertFalse(ReflectLocks.isLocked(obj));
        obj.t = new True(); // T -
        assertTrue(ReflectLocks.isLocked(obj));
        obj.f = new False(); // T F
        assertTrue(ReflectLocks.isLocked(obj));
        obj.t = null; // - F
        assertFalse(ReflectLocks.isLocked(obj));
    }

}
