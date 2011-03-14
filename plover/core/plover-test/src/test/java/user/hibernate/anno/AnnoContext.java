package user.hibernate.anno;

import com.bee32.plover.inject.spring.ContextConfiguration;
import com.bee32.plover.orm.context.TxLegacyContext;

@ContextConfiguration("anno-context.xml")
public class AnnoContext
        extends TxLegacyContext {

}
