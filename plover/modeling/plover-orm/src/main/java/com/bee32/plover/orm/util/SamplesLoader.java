package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
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

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.builtin.IPloverConfManager;
import com.bee32.plover.orm.builtin.StaticPloverConfManager;
import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.dao.MemdbDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.servlet.util.ThreadHttpContext;

@Component
@Lazy
public class SamplesLoader
        implements ApplicationContextAware, ITypeAbbrAware {

    static Logger logger = LoggerFactory.getLogger(SamplesLoader.class);

    @Inject
    IPloverConfManager confManager = StaticPloverConfManager.getInstance();

    @Inject
    CommonDataManager dataManager = MemdbDataManager.getInstance();

    PersistenceUnit unit;

    public SamplesLoader() {
        unit = CustomizedSessionFactoryBean.getForceUnit();
    }

    <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> asFor(Class<? extends E> entityType) {
        IEntityAccessService<E, K> service = dataManager.asFor(entityType);
        return service;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        /**
         * 立即分析 {@link ImportSamples} 依赖关系。
         *
         * ImportSamples
         */
        ImportSamplesUtil.applyImports(applicationContext);
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
    static IdentityHashSet scannedPackages = new IdentityHashSet();

    public synchronized void loadSamples(final SamplePackage pack, final Closure<SampleContribution> progress) {

        class LoadingProcess
                implements Runnable {
            @Override
            public void run() {
                _loadSamples(pack, progress);
            }
        }

        LoadingProcess process = new LoadingProcess();

        // Load without logged-in user stuff.
        ThreadHttpContext.escape(process);
    }

    @SuppressWarnings("unchecked")
    void _loadSamples(SamplePackage pack, Closure<SampleContribution> progress) {
        if (pack == null)
            throw new NullPointerException("pack");

        if (!scannedPackages.add(pack))
            return;

        for (SamplePackage dependency : pack.getDependencies())
            _loadSamples(dependency, progress);

        pack.beginLoad();

        List<Entity<?>> autoList = new ArrayList<Entity<?>>();
        // List<Entity<?>> naturalList = new ArrayList<Entity<?>>();
        List<Entity<?>> specList = new ArrayList<Entity<?>>();

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
                autoList.add(sample);
            else {
                // Serializable naturalId = sample.getNaturalId();
                // if (naturalId == null)
                specList.add(sample);
                // else
                // naturalList.add(sample);
            }
        }

        // Load Side-A (before)
        if (!specList.isEmpty()) {
            // String packAVersionKey = "sampack.a." + pack.getName();
            // String packAVersion = confManager.getConfValue(packAVersionKey);
            String packAPrefix = "sample.";

            boolean packAOnce = true;

            if (packAOnce) {
                // Always save/update spec-entities.

                if (logger.isDebugEnabled()) {
                    String message = String.format("Loading[%d]: %d A-samples from %s", //
                            ++loadIndex, specList.size(), pack);
                    logger.info(message);
                }

                try {
                    IEntityAccessService<Entity<?>, ?> eas = asFor(Entity.class);
                    eas.saveOrUpdateAll(specList);

                    // confManager.setConf(packAVersionKey, "1");
                } catch (Exception e) {
                    logger.error("Failed to load (A) samples from " + pack, e);
                }

            } else {
                for (Entity<?> sample : specList) {
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
                        asFor(sampleType).saveOrUpdate(sample);
                        confManager.setConf(sampleVersionKey, "1");
                        // dataManager.flush();
                    } catch (Exception e) {
                        logger.error("Failed to load (Z) samples from " + pack, e);
                    }
                } // for
            } // packBOnce
        } // A.empty

        // Load Side Z. (after)
        if (!autoList.isEmpty()) {
            String packZVersionKey = "sampack.z." + pack.getName();
            String packZVersion = confManager.getConfValue(packZVersionKey);

            if ("1".equals(packZVersion)) {
                logger.info("  (Z) Already loaded: " + pack.getName());

                // Reload naturals.
                Iterator<Entity<?>> iter = autoList.iterator();
                List<Entity<Serializable>> fixq = new ArrayList<Entity<Serializable>>();

                while (iter.hasNext()) {
                    Entity<Serializable> item = (Entity<Serializable>) iter.next();
                    Class<? extends Entity<?>> itemType = (Class<? extends Entity<?>>) item.getClass();

                    Serializable nid = item.getNaturalId();
                    if (nid != null) {
                        logger.info("      Reload " + itemType.getSimpleName() + " : " + nid);

                        ICriteriaElement selector = item.getSelector();

                        Entity<?> existing;
                        try {
                            existing = asFor(itemType).getUnique(selector);

                        } catch (Exception e) {
                            logger.error("      Failed to reload " + itemType.getSimpleName() + " : " + nid, e);
                            continue;
                        }

                        if (existing == null) {
                            fixq.add(item);
                            continue;
                        }

                        EntityAccessor.setId(item, (Serializable) existing.getId());
                        asFor(itemType).evict(existing);
                    }
                }

                if (!fixq.isEmpty()) {
                    for (Entity<Serializable> item : fixq) {
                        Serializable nid = item.getNaturalId();
                        logger.warn("    Auto fix: readd missing entity " + item.getClass().getSimpleName() + " : "
                                + nid);
                    }
                    try {
                        asFor(Entity.class).saveAll(fixq);
                    } catch (Exception e) {
                        logger.error("      Auto fix failed.", e);
                    }
                }

            } else {
                if (logger.isDebugEnabled()) {
                    String message = String.format("Loading[%d]: %d Z-samples from %s", //
                            ++loadIndex, autoList.size(), pack);
                    logger.info(message);
                }

                try {
                    asFor(Entity.class).saveAll(autoList);

                    confManager.setConf(packZVersionKey, "1");

                    // dataManager.flush();
                } catch (Exception e) {
                    logger.error("Failed to load samples (Z) from " + pack, e);

                    if (logger.isDebugEnabled()) {
                        logger.debug("Samples in the pack: ");
                        for (Entity<?> entity : autoList)
                            logger.debug("    -- " + entity);
                    }
                }

                // more is only belonged to side Z.
                pack.more(dataManager);

            } // loaded?
        } // !sideZ.empty

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
