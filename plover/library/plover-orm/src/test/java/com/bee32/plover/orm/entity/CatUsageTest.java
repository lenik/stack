package com.bee32.plover.orm.entity;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.plover.arch.BuildException;
import com.bee32.plover.arch.util.IStruct;
import com.bee32.plover.arch.util.MapStruct;
import com.bee32.plover.orm.dao.CatDao;
import com.bee32.plover.orm.util.WiredDaoTestCase;

public class CatUsageTest
        extends WiredDaoTestCase {

    public CatUsageTest() {
        super(Animals.getInstance());
    }

    @Test
    public void testPopulateStruct()
            throws BuildException {
        IStruct struct = new MapStruct();
        struct.put("name", "kitty");
        struct.put("color", "pink");

        Cat expected = new Cat("kitty", "pink");

        Cat actual = new CatDao().populate(struct);

        assertEquals(expected, actual);
    }

    @Inject
    CatUsageService catService;

    @Test
    public void testCascade() {
        catService.tcPrepare();
        catService.tcList();
    }

}
