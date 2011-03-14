package com.bee32.plover.orm;

import java.util.Collection;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
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
public class ERModulePostProcessor
        extends ModulePostProcessor {

    @Inject
    SessionFactory sessionFactory;

    @Transactional
    @Override
    public void afterModuleLoaded(IModule module)
            throws Exception {
        if (!(module instanceof ERModule))
            return;

        HibernateTemplate template = new HibernateTemplate(sessionFactory);

        ERModule erModule = (ERModule) module;
        for (Object child : erModule.getChildren()) {
            if (child instanceof EntityRepository<?, ?>) {
                EntityRepository<?, ?> er = (EntityRepository<?, ?>) child;

                Collection<?> normalTransientSamples = er.getTransientSamples(false);

                for (Object sample : normalTransientSamples) {
                    template.save(sample);
                }

            }
        }
    }

}
