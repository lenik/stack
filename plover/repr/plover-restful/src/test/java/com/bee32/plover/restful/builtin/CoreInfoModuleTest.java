package com.bee32.plover.restful.builtin;

import org.junit.Test;

public class CoreInfoModuleTest {

    @Test
    public void testCredit() {
        CoreInfoModule cim = new CoreInfoModule();
        cim.getCredit().getSubjects();
    }

}
