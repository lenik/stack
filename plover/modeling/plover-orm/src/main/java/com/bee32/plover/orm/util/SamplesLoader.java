package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.free.IdentityHashSet;
import javax.inject.Inject;

import org.apache.commons.collections15.Closure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.bee32.plover.arch.util.PriorityComparator;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.inject.spring.ScopeProxy;
import com.bee32.plover.orm.builtin.IPloverConfManager;
import com.bee32.plover.orm.builtin.StaticPloverConfManager;
import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.dao.MemdbDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.util.DiamondPackage.NormalGroup;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.cfg.DBAutoDDL;
import com.bee32.plover.site.scope.PerSite;

@Component
@PerSite
@ScopeProxy(ScopedProxyMode.INTERFACES)
public class SamplesLoader
        implements ISamplesLoader, ITypeAbbrAware {

    static Logger logger = LoggerFactory.getLogger(SamplesLoader.class);

    @Inject
    ApplicationContext appctx;
    @Inject
    IPloverConfManager confManager = StaticPloverConfManager.getInstance();

    @Inject
    CommonDataManager dataManager = MemdbDataManager.getInstance();

    PersistenceUnit unit;

    public SamplesLoader() {
        unit = CustomizedSessionFactoryBean.getForceUnit();
    }

    class _ctx {
        AbstractDataPartialContext data = new AbstractDataPartialContext() {
            @Override
            public CommonDataManager getDataManager() {
                return dataManager;
            }
        };
    }

    _ctx ctx = new _ctx();

    <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> asFor(Class<? extends E> entityType) {
        IEntityAccessService<E, K> service = dataManager.asFor(entityType);
        return service;
    }

    int loadIndex = 0;

    Set<SamplePackage> loadedPackages = new HashSet<>();

    public void loadSamples(Class<? extends SamplePackage> rootPackageClass) {

    }

    @Override
    public void loadSamples(SamplePackage rootPackage) {
        loadSamples(rootPackage, null);
    }

    @Override
    public void loadSamples(SamplePackage rootPackage, Closure<NormalSamples> progress) {
        if (loadedPackages.add(rootPackage)) {
            _loadSamples(rootPackage, progress);
        }
    }

    synchronized void _loadSamples(final SamplePackage rootPackage, final Closure<NormalSamples> progress) {
        logger.debug("Normal samples structure: ");
        SampleDumper.dump(appctx.getBean(NormalGroup.class));

        final List<SamplePackage> queue = new ArrayList<SamplePackage>();

        /**
         * Static: the samples-loader maybe instantiated twice cuz WebAppCtx & AppCtx. So here just
         * make the map static to avoid of duplicates.
         */
        IdentityHashSet scannedPackages = new IdentityHashSet();

        _preLoad(rootPackage, scannedPackages, queue);

        int index = 0;
        for (SamplePackage p : queue) {
            int priority = p.getPriority();
            if (priority == 0) {
                priority = ++index;
                p.setPriority(priority);
            }
        }
        Collections.sort(queue, PriorityComparator.INSTANCE);

        for (int i = 0; i < queue.size(); i++) {
            SamplePackage pack = queue.get(i);
            int objId = ObjectPool.id(pack);
            logger.info("Load-Queue: " + (i + 1) + ", " + pack.getName() + " @" + objId + " :" + pack.getPriority());
        }

        class LoadingProcess
                implements Runnable {
            @Override
            public void run() {
                for (SamplePackage pack : queue) {
                    _actualLoad(pack, progress);
                }
            }
        }

        LoadingProcess process = new LoadingProcess();

        // Load without logged-in user stuff.
        ThreadHttpContext.escape(process);
    }

    void _preLoad(SamplePackage pack, IdentityHashSet set, List<SamplePackage> queue) {
        if (!set.add(pack))
            return;

        for (SamplePackage dependency : pack.getDependencies())
            _preLoad(dependency, set, queue);

        queue.add(pack);
    }

    @SuppressWarnings("unchecked")
    void _actualLoad(SamplePackage pack, Closure<NormalSamples> progress) {
        if (pack == null)
            throw new NullPointerException("pack");

        pack.beginLoad();

        List<Entity<?>> autoList = new ArrayList<Entity<?>>();
        // List<Entity<?>> naturalList = new ArrayList<Entity<?>>();
        List<Entity<?>> fixedList = new ArrayList<Entity<?>>();

        // Classify to A/Z
        for (Entity<?> sample : pack.getSamples()) {
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
                fixedList.add(sample);
                // else
                // naturalList.add(sample);
            }
        }

        // Load Side-A (before)
        if (!fixedList.isEmpty()) {
            // String packAVersionKey = "sampack.a." + pack.getName();
            // String packAVersion = confManager.getConfValue(packAVersionKey);
            String packAPrefix = "sample.";

            boolean packAOnce = true;

            if (packAOnce) {
                // Always save/update fixed-entities.

                if (logger.isDebugEnabled()) {
                    String message = String.format("Loading[%d]: %d A-samples from %s", //
                            ++loadIndex, fixedList.size(), pack);
                    logger.info(message);
                }

                IEntityAccessService<Entity<?>, ?> eas = asFor(Entity.class);
                try {
                    eas.saveOrUpdateAll(fixedList);
                    // confManager.setConf(packAVersionKey, "1");
                } catch (Exception e) {
                    logger.error("Failed to load (A) samples from " + pack, e);

                    DBAutoDDL autoDdl = ThreadHttpContext.getSiteInstance().getAutoDDL();
                    boolean retry = autoDdl == DBAutoDDL.CreateDrop;
                    if (retry)
                        for (Entity<?> fixed1 : fixedList)
                            try {
                                eas.saveOrUpdate(fixed1);
                            } catch (Exception e2) {
                                logger.error(">> Retry one-by-one failed to load (A) sample " + fixed1, e);
                            }
                }

            } else {
                for (Entity<?> sample : fixedList) {
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
                        ctx.data.access(sampleType).saveOrUpdate(sample);
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
                        String itemHint = itemType.getSimpleName() + " : " + nid;
                        logger.info("      Reload " + itemHint);

                        ICriteriaElement selector = item.getSelector();
                        if (selector == null) {
                            logger.warn("    Append (dependant-) transient sample: " + itemHint);
                            fixq.add(item);
                            continue;
                        }

                        Entity<?> existing;
                        try {
                            existing = ctx.data.access(itemType).getUnique(selector);

                        } catch (Exception e) {
                            logger.error("      Failed to reload " + itemType.getSimpleName() + " : " + nid, e);
                            continue;
                        }

                        if (existing == null) {
                            logger.warn("    Append to fix queue: " + itemHint);
                            fixq.add(item);
                            continue;
                        }

                        EntityAccessor.setId(item, (Serializable) existing.getId());
                        ctx.data.access(itemType).evict(existing);
                    }
                }

                if (!fixq.isEmpty()) {
                    for (Entity<Serializable> item : fixq) {
                        Serializable nid = item.getNaturalId();
                        String itemHint = item.getClass().getSimpleName() + " : " + nid;
                        logger.warn("    Auto fix: readd missing entity " + itemHint);
                    }
                    try {
                        ctx.data.access(Entity.class).saveAll(fixq);
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
                    ctx.data.access(Entity.class).saveAll(autoList);

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
                pack.postSave(ctx.data);

            } // loaded?
        } // !sideZ.empty

        pack.endLoad();
    }

}
