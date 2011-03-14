package com.bee32.plover.orm.dao;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.plover.arch.BuildException;
import com.bee32.plover.arch.util.IStruct;
import com.bee32.plover.arch.util.MapStruct;
import com.bee32.plover.orm.entity.Animals;
import com.bee32.plover.orm.entity.Cat;
import com.bee32.plover.orm.util.WiredDaoTestCase;

public class CatDaoTest
        extends WiredDaoTestCase {

    @Inject
    CatDao dao;

    public CatDaoTest() {
        super(Animals.getInstance());
    }

    @Test
    public void testPopulateStruct()
            throws BuildException {
        IStruct struct = new MapStruct();
        struct.put("name", "kitty");
        struct.put("color", "pink");

        Cat expected = new Cat("kitty", "pink");

        Cat actual = dao.populate(struct);

        assertEquals(expected, actual);
    }

    @Test
    public void testSaveLoad() {
        Cat kitty = new Cat("kitty", "pink");

        assertNull(kitty.getId());

        dao.save(kitty);

        assertNotNull(kitty.getId());
    }

}
