package com.bee32.sem.inventory;

import org.junit.Test;

import com.bee32.plover.orm.dao.MemTable;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.QuickDataTestCase;

@Using(SEMInventoryUnit.class)
public class SEMInventorySamplesTest
        extends QuickDataTestCase {

    @Test
    public void test() {
        MemTable.dump();
    }

}
