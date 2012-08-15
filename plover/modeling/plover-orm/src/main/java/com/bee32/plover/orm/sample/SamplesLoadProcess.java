package com.bee32.plover.orm.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections15.Closure;
import org.hibernate.Session;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.inject.NotAComponent;
import com.bee32.plover.orm.builtin.IPloverConfManager;
import com.bee32.plover.orm.builtin.PloverConfDto;
import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityFlags;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.util.ISessionProcedure;
import com.bee32.plover.orm.util.TransactionHelper;

@NotAComponent
public class SamplesLoadProcess
        extends DataService
        implements Runnable {

    static Logger logger = LoggerFactory.getLogger(SamplesLoadProcess.class);

    List<SamplePackage> queue;
    Closure<NormalSamples> progress;
    PersistenceUnit unit;
    IPloverConfManager confManager;
    Map<String, PloverConfDto> section;

    public SamplesLoadProcess(List<SamplePackage> queue, Closure<NormalSamples> progress, IPloverConfManager confManager) {
        if (queue == null)
            throw new NullPointerException("queue");
        this.queue = queue;
        this.progress = progress;
        this.unit = CustomizedSessionFactoryBean.getForceUnit();
        this.confManager = confManager;
        this.section = confManager.getSection(SamplePackage.SECTION);
    }

    @Override
    public void run() {
        // TransactionHelper.openSession(new ISessionProcedure() {
        // @Override
        // public void run(Session session) {
        // processQueue(session);
        // }
        // });
        processQueue();
    }

    void processQueue() {
        SampleLoadStats stats = new SampleLoadStats();
        for (final SamplePackage samplePackage : queue) {
            if (samplePackage.isVirtual())
                continue;

            logger.info("Loading sample package " + samplePackage.getName());
            samplePackage.beginLoad();

            SampleLoadStats packageStats;
            try {
                packageStats = (SampleLoadStats) TransactionHelper.openSession(new ISessionProcedure() {
                    @Override
                    public SampleLoadStats run(Session session) {
                        return loadPackage(session, samplePackage);
                    }
                });
            } catch (Exception e) {
                logger.error("  Failed to load package " + samplePackage.getName(), e);
                continue;
            }
            stats.add(packageStats);

            try {
                samplePackage.postSave(ctx.data);
            } catch (Exception e) {
                logger.error("  Failed to post-save package " + samplePackage.getName(), e);
                continue;
            }

            samplePackage.endLoad();
            confManager.set(SamplePackage.SECTION, samplePackage.getName(), "1");
        }
    }

    /**
     * non-standard: check conf, skip entirely if loaded.
     */
    @SuppressWarnings("unchecked")
    SampleLoadStats loadPackage(Session session, SamplePackage samplePackage) {
        if (samplePackage == null)
            throw new NullPointerException("pack");

        // SessionImpl sessionImpl = (SessionImpl) session;
        SampleLoadStats stats = new SampleLoadStats();

        // DBAutoDDL autoDdl = ThreadHttpContext.getSiteInstance().getAutoDDL();
        // boolean firstTime = autoDdl == DBAutoDDL.CreateDrop;

        PloverConfDto packConfig = section.get(samplePackage.getName());
        boolean addMissings = packConfig == null || samplePackage.getLevel() <= SamplePackage.LEVEL_STANDARD;
        IEntityAccessService<Entity<?>, ?> database = DATA(Entity.class);

        /** 考虑到无法分析 entity 的依赖关系 （需要完整实现prereqs），这里简单的重载所有样本。 */
        addMissings = true;

        SampleList heads = samplePackage.getSamples(true);
        S: for (Entity<?> head : heads) {
            if (head == null)
                throw new NullPointerException("head of micro-group");

            List<Entity<?>> group = new ArrayList<>();
            Entity<?> next = head;
            while (next != null) {
                final Entity<?> micro = next;
                next = EntityAccessor.getNextOfMicroLoop(next);
                Class<? extends Entity<?>> entityType = (Class<? extends Entity<?>>) micro.getClass();
                if (!unit.getClasses().contains(entityType)) {
                    logger.debug("Skipped sample out of unit: " + entityType);
                    continue;
                }

                ICriteriaElement selector = micro.getSelector();
                if (selector == null) {
                    logger.error("Sample without natural-id: " + micro);
                    continue S;
                }

                List<Entity<?>> selection = DATA(entityType).list(selector);
                if (selection.isEmpty()) {
                    if (!addMissings)
                        continue;
                } else {
                    Entity<?> existing = selection.get(0);
                    if (selection.size() > 1)
                        logger.warn("Selector returns multiple results: " + selection + ", choose the first.");
                    if (existing instanceof HibernateProxy) {
                        LazyInitializer lazyInitializer = ((HibernateProxy) existing).getHibernateLazyInitializer();
                        Object target = lazyInitializer.getImplementation(); // (sessionImpl);
                        existing = (Entity<?>) target;
                    }

                    EntityFlags oldFlags = EntityAccessor.getFlags(existing);

                    // FIXME children collection is reset to empty..?
                    // EntityAccessor.setId(micro, existing.getId());
                    micro.retarget(existing);

                    boolean changedForever = oldFlags.isOverrided();
                    if (oldFlags.isLocked() || oldFlags.isUserLock() || changedForever)
                        continue;
                    if (oldFlags.isHidden() || oldFlags.isMarked())
                        continue;

                    // session.clear();
                }

                // Entity<?> clone = micro.clone();
                group.add(micro);
            } // for micro-next

            logger.info("    Micro-Group: ");
            for (Entity<?> ent : group) {
                String typeName = ent.getClass().getSimpleName();
                Object id = ent.getId();
                String title = typeName + " : " + (id == null ? System.identityHashCode(ent) : id);
                logger.info("        " + title);
            }

            session.clear();
            // By each entity type...?
            database.saveOrUpdateAll(group);
            // session.clear();
        } // for sample
        return stats;
    }
}
