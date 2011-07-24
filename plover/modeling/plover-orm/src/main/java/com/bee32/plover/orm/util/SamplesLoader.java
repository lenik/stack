package com.bee32.plover.orm.util;

import java.util.ArrayList;
import java.util.List;

import javax.free.IdentityHashSet;
import javax.inject.Inject;

import org.apache.commons.collections15.Closure;
import org.apache.commons.collections15.functors.NOPClosure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.builtin.PloverConfManager;
import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.unit.PersistenceUnit;

@Component
@Lazy
public class SamplesLoader
        implements ApplicationContextAware, ITypeAbbrAware {

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

    /**
     * Static: the samples-loader maybe instantiated twice cuz WebAppCtx & AppCtx. So here just make
     * the map static to avoid of duplicates.
     */
    static IdentityHashSet loadedPackages = new IdentityHashSet();

    public synchronized void loadSamples(SamplePackage pack, Closure<SampleContribution> progress) {
        if (pack == null)
            throw new NullPointerException("pack");

        if (!loadedPackages.add(pack))
            return;

        for (SamplePackage dependency : pack.getDependencies())
            loadSamples(dependency, progress);

        List<Entity<?>> sideA = new ArrayList<Entity<?>>();
        List<Entity<?>> sideZ = new ArrayList<Entity<?>>();
        pack.beginLoad();

        // Classify to A/Z
        for (Entity<?> sample : pack.getInstances()) {
            if (sample == null) {
                logger.error("Null sample in package " + pack);
                continue;
            }

            Class<?> sampleClass = sample.getClass();
            if (!unit.getClasses().contains(sampleClass)) {
                logger.debug("  Ignored entity of non-using class: " + sampleClass);
                continue;
            }

            boolean isAutoId = EntityAccessor.isAutoId(sample);
            if (isAutoId)
                sideZ.add(sample);
            else
                sideA.add(sample);
        }

        // Load Side-B (before)
        if (!sideA.isEmpty()) {
            String packAVersionKey = "sampack.a." + pack.getName();
            String packAVersion = confManager.getConfValue(packAVersionKey);
            String packAPrefix = "sample.";

            boolean packAOnce = true;

            if (packAOnce) {
                if ("1".equals(packAVersion)) {
                    logger.debug("  (A) Already loaded: " + pack.getName());
                } else {
                    if (logger.isDebugEnabled()) {
                        String message = String.format("Loading[%d]: %d B-samples from %s", //
                                ++loadIndex, sideA.size(), pack);
                        logger.debug(message);
                    }

                    try {
                        @SuppressWarnings("unchecked")
                        IEntityAccessService<Entity<?>, ?> eas = dataManager.asFor(Entity.class);
                        eas.saveAll(sideA);

                        confManager.setConf(packAVersionKey, "1");

                        // dataManager.flush();
                    } catch (Exception e) {
                        logger.error("Failed to load (A) samples from " + pack, e);
                    }
                }

            } else {
                for (Entity<?> sample : sideA) {
                    if (logger.isDebugEnabled()) {
                        String message = String.format("Loading[%d]: Single A-sample from %s", //
                                ++loadIndex, pack);
                        logger.debug(message);
                    }

                    // sample.getVersion();
                    Class<? extends Entity<?>> sampleType = (Class<? extends Entity<?>>) sample.getClass();
                    String typeAbbr = ABBR.abbr(sampleType);
                    String sampleVersionKey = packAPrefix + typeAbbr + "." + sample.getId();
                    String sampleVersion = confManager.getConfValue(sampleVersionKey);

                    if ("NEVER-UPDATE".equals(sampleVersion))
                        continue;

                    try {
                        dataManager.asFor(sampleType).saveOrUpdate(sample);
                        confManager.setConf(sampleVersionKey, "1");
                        // dataManager.flush();
                    } catch (Exception e) {
                        logger.error("Failed to load (Z) samples from " + pack, e);
                    }
                } // for
            } // packBOnce
        } // B.empty

        // Load Side A. (after)
        if (!sideZ.isEmpty()) {
            String packZVersionKey = "sampack.z." + pack.getName();
            String packZVersion = confManager.getConfValue(packZVersionKey);

            if ("1".equals(packZVersion)) {
                logger.debug("  (Z) Already loaded: " + pack.getName());

            } else {
                if (logger.isDebugEnabled()) {
                    String message = String.format("Loading[%d]: %d Z-samples from %s", //
                            ++loadIndex, sideZ.size(), pack);
                    logger.debug(message);
                }

                try {
                    @SuppressWarnings("unchecked")
                    IEntityAccessService<Entity<?>, ?> eas = dataManager.asFor(Entity.class);
                    eas.saveAll(sideZ);

                    confManager.setConf(packZVersionKey, "1");

                    // dataManager.flush();
                } catch (Exception e) {
                    logger.error("Failed to load samples (Z) from " + pack, e);
                }

            }
        } // !sideA.empty

        pack.more(dataManager);

        pack.endLoad();
    }

    public void loadNormalSamples() {
        if (logger.isDebugEnabled()) {
            logger.debug("Normal samples structure: ");
            SampleDumper.dump(DiamondPackage.NORMAL);
        }
        loadSamples(DiamondPackage.NORMAL, NO_PROGRESS);
    }

    public void loadWorseSamples() {
        loadSamples(DiamondPackage.WORSE, NO_PROGRESS);
    }

}
