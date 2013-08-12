package com.bee32.icsf.access.resource;

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

import com.bee32.icsf.access.annotation.AccessCheck;
import com.bee32.plover.arch.ComponentBuilder;
import com.bee32.plover.arch.DataService;
import com.bee32.plover.arch.ui.res.InjectedAppearance;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.res.PropertyDispatcher;

/**
 * 访问点管理器
 *
 * 管理访问点集合
 */
@Component
@Lazy
public class AccessPointManager
        implements ApplicationContextAware, IResourceScanner {

    static Logger logger = LoggerFactory.getLogger(AccessPointManager.class);

    /**
     * XXX - 对于不同装载器装入的 EnterpriseService 或许有不同？可能 ClassLoader-local<scanned> 或许更好。
     */
    static boolean scanned;

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }

    public Collection<AccessPoint> getAccessPoints() {
        scan();
        return AccessPoint.getInstances();
    }

    @Override
    public synchronized void scan() {
        if (scanned)
            return;

        Set<Object> once = new IdentityHashSet();

        for (Class<?> checkBaseClass : checkBaseClasses) {

            for (Object checkInstance : context.getBeansOfType(checkBaseClass).values()) {

                Class<?> checkClass = checkInstance.getClass();
                checkClass = ClassUtil.skipProxies(checkClass);

                if (!once.add(checkClass))
                    continue;

                scanAnnotatedPoints(checkClass);
            }
        }

        scanned = true;
        context = null;
    }

    protected void scanAnnotatedPoints(Class<?> serviceClass) {
        logger.debug("Scan service access points: " + serviceClass);

        URL contextURL = ClassUtil.getContextURL(serviceClass);

        PropertyDispatcher dispatcher = new PropertyDispatcher(serviceClass);

        String section = serviceClass.getSimpleName();
        boolean allMethods = false;

        AccessCheck classAnnotation = serviceClass.getAnnotation(AccessCheck.class);
        if (classAnnotation != null) {
            String name = classAnnotation.name();
            if (!name.isEmpty())
                section = name;
            allMethods = classAnnotation.allMethods();
            // classAnnotation.require();
        }

        for (Method method : serviceClass.getMethods()) {
            AccessCheck annotation = method.getAnnotation(AccessCheck.class);
            if (annotation == null && !allMethods)
                continue;

            String name = annotation.name();
            if (name.isEmpty())
                name = method.getName();
            String pointName = section + "." + name;

            // Skip existing.
            AccessPoint existing = AccessPoint.getInstance(pointName);
            if (existing != null)
                continue;

            InjectedAppearance pointAppearance = new InjectedAppearance(null, contextURL);

            dispatcher.addKeyAcceptor(name, pointAppearance);
            pointAppearance.setPropertyDispatcher(dispatcher);

            logger.debug("Create access point " + pointName);
            AccessPoint point = AccessPoint.create(pointName);

            ComponentBuilder.setAppearance(point, pointAppearance);
        }
    }

    private static final Set<Class<?>> checkBaseClasses = new HashSet<Class<?>>();

    static {
        checkBaseClasses.add(DataService.class);
    }

    public static void registerCheckClass(Class<?> checkClass) {
        checkBaseClasses.add(checkClass);
    }

    public static void unregisterCheckClass(Class<?> checkClass) {
        checkBaseClasses.remove(checkClass);
    }

}
