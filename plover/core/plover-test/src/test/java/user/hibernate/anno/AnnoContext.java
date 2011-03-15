package user.hibernate.anno;

import com.bee32.plover.inject.cref.ContextRef;
import com.bee32.plover.inject.spring.ContextConfiguration;

@ContextConfiguration("anno-context.xml")
public class AnnoContext
        extends ContextRef {

    public AnnoContext() {
        super();
    }

    public AnnoContext(ContextRef... parents) {
        super(parents);
    }

}
