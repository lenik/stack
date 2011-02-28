package com.bee32.plover.orm.util.hibernate;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bee32.plover.orm.dao.HibernateTemplate;
import com.bee32.plover.orm.entity.Cat;
import com.bee32.plover.orm.entity.Tiger;
import com.bee32.plover.orm.unit.PersistenceUnit;

public class TestSessionFactoryBuilderTest {

    PersistenceUnit unit;
    {
        unit = new PersistenceUnit();
        unit.addPersistedClass(Cat.class);
        unit.addPersistedClass(Tiger.class);
    }

    SessionFactory sessionFactory;
    HibernateTemplate template;

    @Before
    public void setup() {
        TestSessionFactoryBuilder factoryBuilder = TestSessionFactoryBuilder.getInstance();
        // factoryBuilder.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        sessionFactory = factoryBuilder.buildForUnits(unit);
        template = new HibernateTemplate(sessionFactory);
    }

    @After
    public void after() {
        if (template != null)
            template.clear(); // flush?
        if (sessionFactory != null)
            sessionFactory.close();
    }

    @Test
    public void testAdd() {
        Cat tomCat = new Cat("Tom", "black");
        Cat lucyCat = new Cat("Lucy", "pink");

        Tiger fishTiger = new Tiger("Fish", "yellow");
        fishTiger.setPower(10);

        template.save(tomCat);
        template.save(lucyCat);
        template.save(fishTiger);
    }

    @Test
    public void testList() {
        for (Cat cat : template.loadAll(Cat.class))
            System.out.println(cat);
    }

}
