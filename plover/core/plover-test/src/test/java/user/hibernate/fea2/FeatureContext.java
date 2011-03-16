package user.hibernate.fea2;

import com.bee32.plover.inject.cref.ContextRef;
import com.bee32.plover.inject.spring.ContextConfiguration;

@ContextConfiguration("context.xml")
public class FeatureContext
        extends ContextRef {

    public FeatureContext() {
        super();
    }

    public FeatureContext(ContextRef... parents) {
        super(parents);
    }

}
