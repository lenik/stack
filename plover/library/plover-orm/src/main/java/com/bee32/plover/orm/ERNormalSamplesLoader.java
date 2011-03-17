package com.bee32.plover.orm;

import java.util.Collection;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.arch.ModulePostProcessor;
import com.bee32.plover.orm.entity.EntityRepository;
import com.bee32.plover.orm.util.ERModule;

@Component
@Lazy
@Transactional
public class ERNormalSamplesLoader
        extends ModulePostProcessor {

    static Logger logger = LoggerFactory.getLogger(ERNormalSamplesLoader.class);

    @Inject
    SessionFactory sessionFactory;

    @Override
    public void afterModuleLoaded(IModule module)
            throws Exception {
        if (!(module instanceof ERModule))
            return;

        ERModule erModule = (ERModule) module;

        loadSamples(erModule);
    }

    @Transactional
    public void loadSamples(ERModule erModule) {
        logger.info("Load ER Samples for module " + erModule.getName());

        HibernateTemplate template = new HibernateTemplate(sessionFactory);

        for (Object child : erModule.getChildren()) {
            if (child instanceof EntityRepository<?, ?>) {
                EntityRepository<?, ?> er = (EntityRepository<?, ?>) child;

                Collection<?> normalTransientSamples = er.getTransientSamples(false);

                for (Object sample : normalTransientSamples) {
                    template.save(sample);

                    // Evict immediately for debug.
                    template.evict(sample);
                }

            }
        }


    }

}
