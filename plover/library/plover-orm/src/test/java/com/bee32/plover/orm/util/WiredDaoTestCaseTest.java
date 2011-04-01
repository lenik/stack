package com.bee32.plover.orm.util;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.dao.CatDao;
import com.bee32.plover.orm.feaCat.Animals;
import com.bee32.plover.orm.feaCat.Cat;
import com.bee32.plover.orm.feaCat.Tiger;
import com.bee32.plover.orm.unit.UseUnit;

@UseUnit(Animals.class)
public class WiredDaoTestCaseTest
        extends WiredDaoTestCase {

    @Inject
    DaoService service;

    @Test
    public void testCreateReload() {
        service.create();
        service.reload();
    }

    @Service
    @Lazy
    static class DaoService
            extends Assert {

        @Inject
        CatDao catDao;

        @Transactional
        public void create() {
            Cat tomCat = new Cat("Tom", "black");
            Cat lucyCat = new Cat("Lucy", "pink");

            Tiger fishTiger = new Tiger("Fish", "yellow");
            fishTiger.setPower(10);

            catDao.save(tomCat);
            catDao.save(lucyCat);
            catDao.save(fishTiger);
        }

        @Transactional(readOnly = true)
        public void reload() {
            List<? extends Cat> cats = catDao.list();

            for (Cat cat : cats) {
                System.out.println(cat);
            }

            assertEquals(3, cats.size());
        }

    }

}
