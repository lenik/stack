package user.hibernate.anno;

import com.bee32.plover.inject.ConfigResourceObject;
import com.bee32.plover.inject.spring.ContextConfiguration;

@ContextConfiguration("anno-context.xml")
public class AnnoContext
        extends ConfigResourceObject {

    public AnnoContext() {
        super();
    }

    public AnnoContext(ConfigResourceObject... parents) {
        super(parents);
    }

}
