package com.bee32.icsf.access.annotation;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.free.IdentityHashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.icsf.access.builtins.PointPermission;
import com.bee32.plover.arch.ComponentBuilder;
import com.bee32.plover.arch.EnterpriseService;
import com.bee32.plover.arch.ui.res.InjectedAppearance;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.res.PropertyDispatcher;

@Component
@Lazy
public class CheckpointDiscoverer
        implements ApplicationContextAware {

    static Logger logger = LoggerFactory.getLogger(CheckpointDiscoverer.class);

    private boolean scanned;
    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.context = applicationContext;
        this.scanned = false;
    }

    public Collection<PointPermission> getCheckpoints() {
        scan();
        return PointPermission.getAllInstances();
    }

    protected synchronized void scan() {
        if (scanned)
            return;

        Set<Object> once = new IdentityHashSet();

        for (Class<?> checkBaseClass : checkBaseClasses) {

            for (Object checkInstance : context.getBeansOfType(checkBaseClass).values()) {

                Class<?> checkClass = checkInstance.getClass();
                if (!once.add(checkClass))
                    continue;

                scanAnnotatedPoints(checkClass);
            }
        }

        scanned = true;
        context = null;
    }

    protected void scanAnnotatedPoints(Class<?> serviceClass) {

        URL contextURL = ClassUtil.getContextURL(serviceClass);

        PropertyDispatcher dispatcher = new PropertyDispatcher(serviceClass);

        String section = serviceClass.getSimpleName();
        boolean allMethods = false;

        Checkpoint classAnnotation = serviceClass.getAnnotation(Checkpoint.class);
        if (classAnnotation != null) {
            String name = classAnnotation.name();
            if (!name.isEmpty())
                section = name;
            allMethods = classAnnotation.allMethods();
            // classAnnotation.require();
        }

        for (Method method : serviceClass.getMethods()) {
            Checkpoint annotation = method.getAnnotation(Checkpoint.class);
            if (annotation == null && !allMethods)
                continue;

            String name = annotation.name();
            if (name.isEmpty())
                name = method.getName();
            String pointName = section + "." + name;

            InjectedAppearance pointAppearance = new InjectedAppearance(null, contextURL);

            dispatcher.addKeyAcceptor(name, pointAppearance);
            pointAppearance.setPropertyDispatcher(dispatcher);

            logger.debug("Create point " + pointName);
            PointPermission point = PointPermission.create(pointName);

            ComponentBuilder.setAppearance(point, pointAppearance);
        }
    }

    private static final Set<Class<?>> checkBaseClasses = new HashSet<Class<?>>();

    static {
        checkBaseClasses.add(EnterpriseService.class);
    }

    public static void registerCheckClass(Class<?> checkClass) {
        checkBaseClasses.add(checkClass);
    }

    public static void unregisterCheckClass(Class<?> checkClass) {
        checkBaseClasses.remove(checkClass);
    }

}
