package com.bee32.plover.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.OrderComparator;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.inject.cref.ScanTestxContext;
import com.bee32.plover.inject.spring.ApplicationContextBuilder;
import com.bee32.plover.inject.spring.Prototype;

@ComponentTemplate
@Prototype
@Import(ScanTestxContext.class)
public abstract class FeaturePlayer<T extends FeaturePlayer<T>>
        extends Assert {

    static Set<IFeaturePlayerSupport> supports;

    public FeaturePlayer() {
        loadFeaturePlayerSupports();
    }

    static synchronized void loadFeaturePlayerSupports() {
        if (supports == null) {
            supports = new TreeSet<IFeaturePlayerSupport>(OrderComparator.INSTANCE);
            for (IFeaturePlayerSupport support : ServiceLoader.load(IFeaturePlayerSupport.class)) {
                supports.add(support);
            }
        }
    }

    private void fireSetup(Class<?> playerClass) {
        for (IFeaturePlayerSupport support : supports)
            support.setup(playerClass);
    }

    private void fireTeardown(Class<?> playerClass) {
        for (IFeaturePlayerSupport support : supports)
            support.teardown(playerClass);
    }

    private void fireInit(ApplicationContext appctx) {
        for (IFeaturePlayerSupport support : supports)
            support.init(appctx);
    }

    private void fireBegin(Object player) {
        for (IFeaturePlayerSupport support : supports)
            support.begin(player);
    }

    private void fireEnd(Object player) {
        for (IFeaturePlayerSupport support : supports)
            support.end(player);
    }

    protected static BufferedReader stdin;
    static {
        stdin = new BufferedReader(new InputStreamReader(System.in));
    }

    protected void mainLoop(ICoordinator<T> coordinator)
            throws IOException {

        Class<T> playerClass = ClassUtil.infer1(getClass(), FeaturePlayer.class, 0);

        fireSetup(playerClass);

        ApplicationContext appctx = ApplicationContextBuilder.buildSelfHostedContext(playerClass);
        AutowireCapableBeanFactory beanFactory = appctx.getAutowireCapableBeanFactory();

        fireInit(appctx);

        while (true) {
            try {
                T player = beanFactory.createBean(playerClass);

                fireBegin(player);
                {
                    coordinator.main(player);
                }
                fireEnd(player);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.err.println("--------------------------------------------------");
            System.err.println("Press enter to try again");
            String line = stdin.readLine();

            if (line == null || line.trim().equals("q"))
                break;
        }

        fireTeardown(playerClass);
    }

}
