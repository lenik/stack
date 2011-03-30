package com.bee32.plover.orm.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.free.IllegalUsageException;
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

    boolean prescan = false;

    // prescan == false
    ApplicationContext applicationContext;

    // prescan == true
    Set<String> contributionNames;
    Map<String, Collection<IEntity<?>>> allNormalSamples;
    Map<String, Collection<IEntity<?>>> allWorseSamples;

    public SamplesLoader() {
        if (prescan) {
            contributionNames = new HashSet<String>();
            allNormalSamples = new TreeMap<String, Collection<IEntity<?>>>();
            allWorseSamples = new TreeMap<String, Collection<IEntity<?>>>();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {

        if (prescan) {
            // Scan all contributions and import them.
            for (EntitySamplesContribution contrib : applicationContext.//
                    getBeansOfType(EntitySamplesContribution.class).values()) {

                String name = contrib.getName();
                if (!contributionNames.add(name))
                    throw new IllegalUsageException("Contribution name " + name + " is not unique.");

                Collection<IEntity<?>> normalSamples = contrib.getTransientSamples(true);
                Collection<IEntity<?>> worseSamples = contrib.getTransientSamples(false);

                if (normalSamples != null && !normalSamples.isEmpty())
                    allNormalSamples.put(name, normalSamples);

                if (worseSamples != null && !worseSamples.isEmpty())
                    allWorseSamples.put(name, worseSamples);
            }
        } else {
            this.applicationContext = applicationContext;
        }
    }

    static int index = 0;

    public void loadNormalSamples() {
        if (prescan)
            throw new UnsupportedOperationException("prescan");

        for (EntitySamplesContribution contrib : applicationContext.//
                getBeansOfType(EntitySamplesContribution.class).values()) {

            logger.info("Load normal sample contribution[" + index++ + "]: " + contrib);

            loadSamples(contrib, false);

        }
    }

    public void loadWorseSamples() {
        if (prescan)
            throw new UnsupportedOperationException("prescan");

        for (EntitySamplesContribution contrib : applicationContext.//
                getBeansOfType(EntitySamplesContribution.class).values()) {

            logger.info("Load worse sample contribution[" + index++ + "]: " + contrib);

            loadSamples(contrib, true);

        }
    }

    public synchronized void loadSamples(IEntitySamplesContribution contrib, boolean worseCase) {

        if (contrib == null)
            throw new NullPointerException("contrib");

        if (contrib.isLoaded())
            return;

        contrib.getTransientSamples(worseCase);

        Collection<? extends IEntity<?>> samples = contrib.getTransientSamples(false);
        if (samples == null || samples.isEmpty()) {
            logger.debug("  No sample defined in " + contrib);
            return;
        }

        if (logger.isDebugEnabled()) {
            String message = String.format("  Persisting %d samples for %s", samples.size(), contrib);
            logger.debug(message);
        }

        dataManager.saveAll(samples);

        contrib.setLoaded(true);
    }

}
