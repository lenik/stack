package user.hibernate.fea2;

import com.bee32.plover.inject.cref.ContextRef;
import com.bee32.plover.inject.spring.ContextConfiguration;

@ContextConfiguration("context.xml")
public class LocalContext
        extends ContextRef {

    public LocalContext() {
        super();
    }

    public LocalContext(ContextRef... parents) {
        super(parents);
    }

}
