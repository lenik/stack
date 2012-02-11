package com.bee32.plover.orm.dao;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.bee32.plover.orm.feaCat.AnimalUnit;
import com.bee32.plover.orm.feaCat.Cat;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.QuickDataTestCase;

@Using(AnimalUnit.class)
public class MemdbDataManagerTest
        extends QuickDataTestCase {

    @Before
    public void buildSamples() {
        ctx.data.access(Cat.class).deleteAll();

        Cat cat1 = new Cat("Jerry", "red");
        Cat cat2 = new Cat("Jimmy", "red");

        ctx.data.access(Cat.class).saveAll(cat1, cat2);
    }

    @Test
    public void testSave() {
        Cat jerry = ctx.data.access(Cat.class).getByName("Jerry");
        assertNotNull(jerry);

        long id = jerry.getId();
        assertEquals(1L, id);
    }

    @Test
    public void testList() {
        List<Cat> cats = ctx.data.access(Cat.class).list();
        assertEquals(2, cats.size());
        System.out.println(cats);
    }

}
