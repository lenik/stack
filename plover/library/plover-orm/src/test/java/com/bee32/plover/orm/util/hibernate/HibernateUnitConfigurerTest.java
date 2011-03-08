package com.bee32.plover.orm.util.hibernate;

import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bee32.plover.orm.entity.Animals;
import com.bee32.plover.orm.entity.Cat;
import com.bee32.plover.orm.entity.Tiger;

public class HibernateUnitConfigurerTest
        extends HibernateUnitConfigurer {

    public HibernateUnitConfigurerTest() {
        super(new TestHibernateConfigurer(), Animals.getInstance());
    }

    @Test
    public void testAdd() {
        Cat tomCat = new Cat("Tom", "black");
        Cat lucyCat = new Cat("Lucy", "pink");

        Tiger fishTiger = new Tiger("Fish", "yellow");
        fishTiger.setPower(10);

        HibernateTemplate template = getHibernateTemplate();

        template.save(tomCat);
        template.save(lucyCat);
        template.save(fishTiger);
    }

    @Test
    public void testList() {
        HibernateTemplate template = getHibernateTemplate();

        for (Cat cat : template.loadAll(Cat.class))
            System.out.println(cat);
    }

}
