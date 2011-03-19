package com.bee32.plover.orm;

import java.util.Collection;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.arch.ModulePostProcessor;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.EntityRepository;
import com.bee32.plover.orm.util.ERModule;

@Component
@Lazy
public class ERNormalSamplesLoader
        extends ModulePostProcessor {

    static Logger logger = LoggerFactory.getLogger(ERNormalSamplesLoader.class);

    @Inject
    CommonDataManager dataManager;

    @Override
    public void afterModuleLoaded(IModule module)
            throws Exception {
        if (!(module instanceof ERModule))
            return;

        ERModule erModule = (ERModule) module;

        loadSamples(erModule);
    }

    public void loadSamples(ERModule erModule) {
        logger.info("Load ER Samples for module " + erModule.getName());

        for (Object child : erModule.getChildren()) {

            if (child instanceof EntityRepository<?, ?>) {
                EntityRepository<?, ?> entityRepository = (EntityRepository<?, ?>) child;

                String erName = entityRepository.getName();
                // + "/" + entityRepository.getClass().getSimpleName();

                Collection<?> normalSamples = entityRepository.getTransientSamples(false);
                if (normalSamples == null || normalSamples.isEmpty()) {
                    logger.debug("  No normal sample defined in " + erName);
                    continue;
                }

                if (logger.isDebugEnabled()) {
                    String message = String.format("  Load %d normal samples for %s", normalSamples.size(), erName);
                    logger.debug(message);
                }

                dataManager.saveAll(normalSamples);
            }

        }

    }

}
