package com.bee32.plover.inject.scope;

import java.util.HashMap;
import java.util.Map;

public class ThreadGroupScope
        extends AbstractScope {

    Map<ThreadGroup, Map<String, Object>> tglBeanMap;

    public ThreadGroupScope() {
        tglBeanMap = new HashMap<ThreadGroup, Map<String, Object>>();
    }

    @Override
    protected synchronized Map<String, Object> getBeanMap() {
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        Map<String, Object> beanMap = tglBeanMap.get(threadGroup);
        if (beanMap == null) {
            beanMap = new HashMap<String, Object>();
            tglBeanMap.put(threadGroup, beanMap);
        }
        return beanMap;
    }

}
