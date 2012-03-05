package com.bee32.plover.orm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections15.Closure;
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
        TransactionHelper.openSession(new Runnable() {
            @Override
            public void run() {
                processQueue();
            }
        });
    }

    void processQueue() {
        for (SamplePackage pack : queue) {
            logger.info("Loading sample package " + pack.getName());
            pack.beginLoad();

            try {
                loadPackage(pack);
            } catch (Exception e) {
                logger.error("  Failed to load package " + pack.getName(), e);
                continue;
            }

            try {
                pack.postSave(ctx.data);
            } catch (Exception e) {
                logger.error("  Failed to post-save package " + pack.getName(), e);
                continue;
            }

            pack.endLoad();
            confManager.set(SamplePackage.SECTION, pack.getName(), "1");
        }
    }

    /**
     * non-standard: check conf, skip entirely if loaded.
     *
     * @throws CloneNotSupportedException
     */
    @SuppressWarnings("unchecked")
    void loadPackage(SamplePackage pack)
            throws CloneNotSupportedException {
        if (pack == null)
            throw new NullPointerException("pack");

        // DBAutoDDL autoDdl = ThreadHttpContext.getSiteInstance().getAutoDDL();
        // boolean firstTime = autoDdl == DBAutoDDL.CreateDrop;

        PloverConfDto packConfig = section.get(pack.getName());
        boolean addMissings = packConfig == null || pack.getLevel() <= SamplePackage.LEVEL_STANDARD;
        IEntityAccessService<Entity<?>, ?> database = ctx.data.access(Entity.class);

        /** 考虑到无法分析 entity 的倚赖关系 （需要完整实现prereqs），这里简单的重载所有样本。 */
        addMissings = true;

        S: for (Entity<?> micro1 : pack.getSamples()) {
            if (micro1 == null)
                throw new NullPointerException("sample");

            List<Entity<?>> group = new ArrayList<>();
            Entity<?> next = micro1;
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

                List<Entity<?>> selection = ctx.data.access(entityType).list(selector);
                if (selection.isEmpty()) {
                    if (!addMissings)
                        continue;
                } else {
                    final Entity<?> existing = selection.get(0);
                    if (selection.size() > 1)
                        logger.warn("Selector returns multiple results: " + selection + ", choose the first.");

                    EntityFlags ef = EntityAccessor.getFlags(existing);
                    if (ef.isWeakData() || ef.isLocked() || ef.isUserLock())
                        continue;
                    if (ef.isHidden() || ef.isMarked())
                        continue;

                    // EntityAccessor.setId(micro, existing.getId());
                    micro.retarget(existing);
                    ctx.data.access(entityType).evict(existing);
                    // ctx.data.access(entityType).flush();
                }

                Entity<?> clone = micro.clone();
                group.add(clone);
            } // for micro-next

            logger.info("    Micro-Group: ");
            for (Entity<?> ent : group) {
                String typeName = ent.getClass().getSimpleName();
                Object id = ent.getId();
                String title = typeName + " : " + (id == null ? System.identityHashCode(ent) : id);
                logger.info("        " + title);
            }

            // By each entity type...?
            database.saveOrUpdateAll(group);
            for (Entity<?> entity : group)
                database.evict(entity);
        } // for sample
    }

}
