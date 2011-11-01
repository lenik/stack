package com.bee32.plover.inject.scope;

import java.util.HashMap;
import java.util.Map;

public class ThreadScope
        extends AbstractScope {

    ThreadLocal<Map<String, Object>> tlBeanMap;

    public ThreadScope() {
        tlBeanMap = new ThreadLocal<Map<String, Object>>() {

            @Override
            protected Map<String, Object> initialValue() {
                return new HashMap<>();
            }

        };
    }

    @Override
    protected Map<String, Object> getBeanMap() {
        Map<String, Object> beanMap = tlBeanMap.get();
        return beanMap;
    }

}
