package com.bee32.plover.orm.util;

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

import com.bee32.plover.inject.spring.ScopeProxy;
import com.bee32.plover.orm.builtin.IPloverConfManager;
import com.bee32.plover.orm.builtin.StaticPloverConfManager;
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
    public void loadSamples(SamplePackage maxPackage, Closure<NormalSamples> progress) {
        // Scan and inject dependencies to super packages.
        appctx.getBeansOfType(SamplePackage.class);

        logger.debug("Normal samples structure: ");
        SampleDumper.dump(appctx.getBean(Normals.class));

        List<SamplePackage> queue = new ArrayList<SamplePackage>();
        prepareLoadQueue(maxPackage, new IdentityHashSet(), queue);

        int seq = 0;
        for (SamplePackage p : queue)
            p.setSeq(++seq);
        Collections.sort(queue, SamplePackageComparator.INSTANCE);

        for (int i = 0; i < queue.size(); i++) {
            SamplePackage pack = queue.get(i);
            int objId = ObjectPool.id(pack);
            logger.info("Load-Queue: " + (i + 1) + ", " + pack.getName() + " @" + objId + " :" + pack.getPriority());
        }

        SamplesLoadProcess process = new SamplesLoadProcess(queue, progress, confManager);

        // Load without logged-in user stuff.
        ThreadHttpContext.escape(process);
    }

    void prepareLoadQueue(SamplePackage pack, IdentityHashSet pset, List<SamplePackage> queue) {
        if (!pset.add(pack))
            return;

        for (SamplePackage dependency : pack.getDependencies())
            prepareLoadQueue(dependency, pset, queue);

        queue.add(pack);
    }

}
