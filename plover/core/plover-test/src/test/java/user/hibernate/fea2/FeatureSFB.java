package user.hibernate.fea2;

import user.hibernate.fea2.ext.Banana;
import user.hibernate.fea2.ext.Food;

import com.bee32.plover.orm.config.TestPurposeSessionFactoryBean;

public class FeatureSFB
        extends TestPurposeSessionFactoryBean {

    {
        addPersistedClass(Food.class);
        addPersistedClass(Fruit.class);
        addPersistedClass(Banana.class);

        addPersistedClass(Friend.class);
    }

}
