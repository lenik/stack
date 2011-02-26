package com.bee32.plover.orm.util.hibernate;

import org.hibernate.SessionFactory;
import org.junit.Test;

import com.bee32.plover.orm.dao.HibernateTemplate;
import com.bee32.plover.orm.entity.Cat;
import com.bee32.plover.orm.entity.Tiger;
import com.bee32.plover.orm.unit.PersistenceUnit;

public class TestSessionFactoryProviderTest {

    PersistenceUnit unit;
    {
        unit = new PersistenceUnit();
        unit.addPersistedClass(Cat.class);
        unit.addPersistedClass(Tiger.class);
    }

    SessionFactory sessionFactory = TestSessionFactoryProvider.getInstance().getSessionFactory(unit);
    HibernateTemplate template = new HibernateTemplate(sessionFactory);

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
    public void list() {
        for (Cat cat : template.loadAll(Cat.class))
            System.out.println(cat);
    }

}
