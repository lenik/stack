package com.bee32.plover.orm.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.IEntity;

@Component
@Lazy
public class SamplesLoader
        implements ApplicationContextAware {

    static Logger logger = LoggerFactory.getLogger(SamplesLoader.class);

    @Inject
    CommonDataManager dataManager;

    Map<Class<?>, IEntitySamplesContribution> dependencies;

    public SamplesLoader() {
        dependencies = new HashMap<Class<?>, IEntitySamplesContribution>();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {

        // Scan all contributions and import them.
        for (EntitySamplesContribution contrib : applicationContext.//
                getBeansOfType(EntitySamplesContribution.class).values()) {

            Class<?> contribClass = contrib.getClass();
            dependencies.put(contribClass, contrib);
        }
    }

    public void loadNormalSamples() {
        for (IEntitySamplesContribution contrib : dependencies.values()) {
            loadSamples(contrib, false);
        }
    }

    public void loadWorseSamples() {
        for (IEntitySamplesContribution contrib : dependencies.values()) {
            loadSamples(contrib, true);

        }
    }

    static int loadIndex = 0;

    public synchronized void loadSamples(IEntitySamplesContribution contrib, boolean worseCase) {

        if (contrib == null)
            throw new NullPointerException("contrib");

        if (contrib.isLoaded())
            return;

        ImportSamples imports = contrib.getClass().getAnnotation(ImportSamples.class);
        if (imports != null) {
            for (Class<?> importClass : imports.value()) {
                IEntitySamplesContribution dependency = dependencies.get(importClass);
                loadSamples(dependency, worseCase);
            }
        }

        contrib.getTransientSamples(worseCase);

        Collection<? extends IEntity<?>> samples = contrib.getTransientSamples(false);
        if (samples == null || samples.isEmpty()) {
            logger.debug("  No sample defined in " + contrib);
            return;
        }

        if (logger.isDebugEnabled()) {
            String message = String.format("Loading[%d]: %d %s samples from %s", ++loadIndex, samples.size(), //
                    worseCase ? "worse" : "normal", contrib);
            logger.debug(message);
        }

        dataManager.saveAll(samples);

        contrib.setLoaded(true);
    }

}
