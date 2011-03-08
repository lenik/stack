package com.bee32.plover.orm.entity;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.plover.arch.BuildException;
import com.bee32.plover.arch.util.IStruct;
import com.bee32.plover.arch.util.MapStruct;
import com.bee32.plover.orm.util.hibernate.HibernateConfigurer;
import com.bee32.plover.orm.util.hibernate.HibernateUnitConfigurer;
import com.bee32.plover.test.WiredAssembledTestCase;

public class CatRepoTest
        extends WiredAssembledTestCase {

    @Inject
    HibernateConfigurer hibernateConfigurer;

    HibernateUnitConfigurer hl;

    public CatRepoTest() {
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        install(hl = new HibernateUnitConfigurer(hibernateConfigurer, Animals.getInstance()));
    }

    @Test
    public void testPopulate()
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
        repo.setSessionFactory(hl.getSessionFactory());

        Cat kitty = new Cat("kitty", "pink");
        repo.save(kitty);
    }

}
