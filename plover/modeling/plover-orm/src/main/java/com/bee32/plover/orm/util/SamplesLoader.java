package com.bee32.plover.orm.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections15.Closure;
import org.apache.commons.collections15.functors.NOPClosure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.builtin.PloverConfManager;
import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.unit.PersistenceUnit;

@Component
@Lazy
public class SamplesLoader
        implements ApplicationContextAware {

    static Logger logger = LoggerFactory.getLogger(SamplesLoader.class);

    @Inject
    PloverConfManager confManager;

    @Inject
    CommonDataManager dataManager;

    PersistenceUnit unit;

    public SamplesLoader() {
        unit = CustomizedSessionFactoryBean.getForceUnit();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {

        // Eagerly scan all the contributions.
        for (SampleContribution contrib : ImportSamplesUtil.applyImports(applicationContext))
            ;
    }

    private static Closure<SampleContribution> NO_PROGRESS;
    static {
        NO_PROGRESS = NOPClosure.getInstance();
    }

    static int loadIndex = 0;

    public synchronized void loadSamples(SampleContribution contrib, boolean worseCase,
            Closure<SampleContribution> progress) {

        if (contrib == null)
            throw new NullPointerException("contrib");

        if (contrib.isLoaded())
            return;

        ImportSamples imports = contrib.getClass().getAnnotation(ImportSamples.class);
        if (imports != null) {
            for (Class<?> importClass : imports.value()) {
                SampleContribution dependency = contributions.get(importClass);
                if (dependency == null) {
                    logger.warn("Samples contribution " + contrib + " imports non-existing contribution " + importClass);
                    continue;
                }
                loadSamples(dependency, worseCase, progress);
            }
        }

        String loadKey = "loaded." + contrib.getClass().getName();
        String loadedState = confManager.getConfValue(loadKey);

        if ("1".equals(loadedState)) {
            logger.debug("  Already loaded: " + loadKey);

        } else {
            List<Entity<?>> selection = new ArrayList<Entity<?>>();

            contrib.beginLoad();

            Collection<? extends Entity<?>> samples = contrib.getTransientSamples(worseCase);
            if (samples == null || samples.isEmpty()) {
                logger.debug("  No sample defined in " + contrib);
                return;
            }

            for (Entity<?> sample : samples) {
                if (sample == null) {
                    logger.error("Null sample in contribution " + contrib);
                    continue;
                }

                Class<?> sampleClass = sample.getClass();
                if (unit.getClasses().contains(sampleClass))
                    selection.add(sample);
                else
                    logger.debug("  Ignored entity of non-using class: " + sampleClass);
            }

            if (logger.isDebugEnabled()) {
                String message = String.format("Loading[%d]: %d %s samples from %s", ++loadIndex, selection.size(), //
                        worseCase ? "worse" : "normal", contrib);
                logger.debug(message);
            }

            progress.execute(contrib);

            try {
                @SuppressWarnings("unchecked")
                IEntityAccessService<Entity<?>, ?> eas = dataManager.access(Entity.class);
                eas.saveAll(selection);

                confManager.setConf(loadKey, "1");

                // dataManager.flush();
            } catch (DataAccessException e) {
                throw new RuntimeException("Failed to load samples from " + contrib, e);
            }

            contrib.endLoad();
        }

        contrib.setLoaded(true);
    }

}
