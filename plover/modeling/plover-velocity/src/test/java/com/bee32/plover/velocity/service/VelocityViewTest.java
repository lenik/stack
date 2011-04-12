package com.bee32.plover.velocity.service;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.restful.IRESTfulView;
import com.bee32.plover.restful.RESTfulViewFactory;

public class VelocityViewTest
        extends Assert {

    @Test
    public void testServiceLoader() {
        Set<IRESTfulView> views = RESTfulViewFactory.getViews();
        Set<Class<?>> viewClasses = ClassUtil.getClasses(views);
        assertTrue(viewClasses.contains(VelocityView.class));
    }

}
