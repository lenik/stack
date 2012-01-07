package com.bee32.plover.orm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.annotation.EntityTypePattern;
import com.bee32.plover.orm.annotation.ForEntityUtil;

@Component
public class EntityViewBeanRegistry
        implements ApplicationContextAware {

    final Map<EntityTypePattern, List<Class<? extends EntityViewBean>>> reverseMap;

    public EntityViewBeanRegistry() {
        reverseMap = new HashMap<EntityTypePattern, List<Class<? extends EntityViewBean>>>();
    }

    @Override
    public void setApplicationContext(ApplicationContext appctx)
            throws BeansException {
        String[] beanNames = appctx.getBeanNamesForType(EntityViewBean.class, true, false);
        for (String beanName : beanNames) {
            Class<?> beanType = appctx.getType(beanName);
            assert (EntityViewBean.class.isAssignableFrom(beanType));
            @SuppressWarnings("unchecked")
            Class<? extends EntityViewBean> viewBeanType = (Class<? extends EntityViewBean>) beanType;

            // entityViewBeanType.getDeclaredAnnotations();
            for (EntityTypePattern pattern : ForEntityUtil.getEntitTypePatterns(viewBeanType)) {
                List<Class<? extends EntityViewBean>> viewBeanClasses = getViewBeanClasses(pattern);
                viewBeanClasses.add(viewBeanType);
            }
        }
    }

    public synchronized List<Class<? extends EntityViewBean>> getViewBeanClasses(EntityTypePattern pattern) {
        List<Class<? extends EntityViewBean>> list = reverseMap.get(pattern);
        if (list == null) {
            list = new ArrayList<Class<? extends EntityViewBean>>();
            reverseMap.put(pattern, list);
        }
        return list;
    }

    public void dump() {
        for (Entry<EntityTypePattern, List<Class<? extends EntityViewBean>>> entry : reverseMap.entrySet()) {
            EntityTypePattern pattern = entry.getKey();
            List<Class<? extends EntityViewBean>> viewBeanTypes = entry.getValue();
            System.out.println(pattern + ": ");
            for (Class<?> viewBeanType : viewBeanTypes)
                System.out.println("    " + viewBeanType.getCanonicalName());
        }
    }

}
