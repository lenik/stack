package user.spring;

import org.springframework.beans.factory.InitializingBean;

public class LazyInherited
        extends LazyRoot
        implements InitializingBean {

    public LazyInherited() {
        System.err.println("LazyInherited::ctor()");
    }

    @Override
    public void afterPropertiesSet()
            throws Exception {
        System.err.println("LazyInherited::afterProeprtiesSet()");
    }

    static {
        System.err.println("LazyInherited::static {}");
    }

}
