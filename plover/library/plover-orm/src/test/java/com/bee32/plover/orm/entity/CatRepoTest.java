package com.bee32.plover.orm.entity;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.bee32.plover.arch.BuildException;
import com.bee32.plover.orm.util.hibernate.HibernateSessionTestCase;

public class CatRepoTest
        extends HibernateSessionTestCase {

    public CatRepoTest() {
        super(Animals.getInstance());
    }

    @Test
    public void testPopulate()
            throws BuildException {
        Map<String, String> struct = new HashMap<String, String>();
        struct.put("name", "kitty");
        struct.put("color", "pink");

        Cat expected = new Cat("kitty", "pink");

        CatRepo repo = new CatRepo();
        Cat actual = repo.populate(struct);

        assertEquals(expected, actual);
    }

}
