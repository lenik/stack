package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.free.IdentityHashSet;
import javax.inject.Inject;

import org.apache.commons.collections15.Closure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.inject.spring.ScopeProxy;
import com.bee32.plover.orm.builtin.IPloverConfManager;
import com.bee32.plover.orm.builtin.StaticPloverConfManager;
import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.dao.MemdbDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityFlags;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.util.SuperSamplePackage.Normals;
import com.bee32.plover.servlet.util.ThreadHttpContext;
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
        DataPartialContext data = new DataPartialContext() {
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

    public void loadSamples(Class<? extends SamplePackage> maxPackageClass) {
        SamplePackage maxPackage = appctx.getBean(maxPackageClass);
        loadSamples(maxPackage);
    }

    @Override
    public void loadSamples(SamplePackage maxPackage) {
        loadSamples(maxPackage, null);
    }

    /**
     * <strike>Static: the samples-loader maybe instantiated twice cuz WebAppCtx & AppCtx. So here
     * just make the map static to avoid of duplicates.</strike>
     */
    @Override
    public void loadSamples(SamplePackage maxPackage, final Closure<NormalSamples> progress) {
        // Scan and inject dependencies to super packages.
        appctx.getBeansOfType(SamplePackage.class);

        logger.debug("Normal samples structure: ");
        SampleDumper.dump(appctx.getBean(Normals.class));

        final List<SamplePackage> queue = new ArrayList<SamplePackage>();

        _preLoad(maxPackage, new IdentityHashSet(), queue);

        int seq = 0;
        for (SamplePackage p : queue)
            p.setSeq(++seq);
        Collections.sort(queue, SamplePackageComparator.INSTANCE);

        for (int i = 0; i < queue.size(); i++) {
            SamplePackage pack = queue.get(i);
            int objId = ObjectPool.id(pack);
            logger.info("Load-Queue: " + (i + 1) + ", " + pack.getName() + " @" + objId + " :" + pack.getPriority());
        }

        class LoadingProcess
                implements Runnable {
            @Override
            public void run() {
                for (SamplePackage singlePackage : queue) {
                    _actualLoad(singlePackage, progress);
                }
            }
        }

        LoadingProcess process = new LoadingProcess();

        // Load without logged-in user stuff.
        ThreadHttpContext.escape(process);
    }

    void _preLoad(SamplePackage pack, IdentityHashSet pset, List<SamplePackage> queue) {
        if (!pset.add(pack))
            return;

        for (SamplePackage dependency : pack.getDependencies())
            _preLoad(dependency, pset, queue);

        queue.add(pack);
    }

    @SuppressWarnings("unchecked")
    void _actualLoad(SamplePackage pack, Closure<NormalSamples> progress) {
        if (pack == null)
            throw new NullPointerException("pack");

// String packZVersionKey = "sampack.z." + pack.getName();
// String packZVersion = confManager.getConfValue(packZVersionKey);
//
// DBAutoDDL autoDdl = ThreadHttpContext.getSiteInstance().getAutoDDL();
// boolean retry = autoDdl == DBAutoDDL.CreateDrop;

        pack.beginLoad();

        // Classify to A/Z
        for (Entity<?> sample : pack.getSamples()) {
            if (sample == null) {
                logger.error("Null sample in package " + pack);
                continue;
            }

            Class<? extends Entity<?>> sampleClass = (Class<? extends Entity<?>>) sample.getClass();
            if (!unit.getClasses().contains(sampleClass)) {
                logger.debug("Skipped sample out of unit: " + sampleClass);
                continue;
            }

            ICriteriaElement selector = sample.getSelector();
            if (selector == null) {
                logger.error("Sample without natural-id: " + sample);
                continue;
            }

            Entity<?> existing = ctx.data.access(sampleClass).getUnique(selector);
            if (existing != null) {
                EntityFlags ef = EntityAccessor.getFlags(existing);
                if (ef.isWeakData() || ef.isLocked() || ef.isUserLock())
                    continue;
                if (ef.isHidden() || ef.isMarked())
                    continue;
            }
            EntityAccessor.setId(sample, existing.getId());
            ctx.data.access(sampleClass).evict(existing);
            ctx.data.access(sampleClass).saveOrUpdate(sample);
        } // !sideZ.empty

        pack.postSave(ctx.data);
        pack.endLoad();
    }

}
