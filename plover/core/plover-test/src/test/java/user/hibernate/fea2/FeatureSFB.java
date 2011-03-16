package user.hibernate.fea2;

import user.hibernate.fea2.ext.Banana;

import com.bee32.plover.orm.config.TestPurposeSessionFactoryBean;

public class FeatureSFB
        extends TestPurposeSessionFactoryBean {

    {
        addPersistedClass(Friend.class);
        addPersistedClass(Fruit.class);
        addPersistedClass(Banana.class);
    }

}
