package com.bee32.plover.orm.entity;

import org.junit.Test;

import com.bee32.plover.arch.BuildException;
import com.bee32.plover.arch.util.IStruct;
import com.bee32.plover.arch.util.MapStruct;
import com.bee32.plover.orm.util.WiredDaoTestCase;

public class CatRepoTest
        extends WiredDaoTestCase {

    public CatRepoTest() {
        super(Animals.getInstance());
    }

    @Test
    public void testPopulateStruct()
            throws BuildException {
        IStruct struct = new MapStruct();
        struct.put("name", "kitty");
        struct.put("color", "pink");

        Cat expected = new Cat("kitty", "pink");

        CatRepo repo = new CatRepo();
        Cat actual = repo.populate(struct);

        assertEquals(expected, actual);
    }

    @Test
    public void testSaveLoad() {
        CatRepo repo = new CatRepo();
        repo.setSessionFactory(getSessionFactory());

        Cat kitty = new Cat("kitty", "pink");
        repo.save(kitty);
    }

}
