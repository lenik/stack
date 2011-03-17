package user.hibernate.fea2;

import user.hibernate.fea2.ext.Banana;
import user.hibernate.fea2.ext.Food;

import com.bee32.plover.orm.config.TestPurposeSessionFactoryBean;
import com.bee32.plover.orm.unit.PersistenceUnit;

public class FeatureSFB
        extends TestPurposeSessionFactoryBean {

    static final PersistenceUnit unit = new PersistenceUnit("fea2");
    static {

        unit.addPersistedClass(Food.class);
        unit.addPersistedClass(Fruit.class);
        unit.addPersistedClass(Banana.class);

        unit.addPersistedClass(Friend.class);
    }

    {
        testUnit = unit;
    }

}
