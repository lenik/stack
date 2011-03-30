package com.bee32.plover.orm.util;

import java.util.Collection;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.IEntity;

@Component
@Lazy
public class SamplesLoader {

    static Logger logger = LoggerFactory.getLogger(SamplesLoader.class);

    @Inject
    CommonDataManager dataManager;

    public void loadSamples(IEntitySamplesContribution contrib, boolean worseCase) {

        String category = worseCase ? "worse" : "normal";

        if (contrib == null)
            throw new NullPointerException("contrib");

        logger.info("Load " + category + " samples for " + contrib);

        contrib.getTransientSamples(worseCase);

        Collection<? extends IEntity<?>> samples = contrib.getTransientSamples(false);
        if (samples == null || samples.isEmpty()) {
            logger.debug("  No " + category + " sample defined in " + contrib);
            return;
        }

        if (logger.isDebugEnabled()) {
            String message = String.format("  Loading %d normal samples for %s", samples.size(), contrib);
            logger.debug(message);
        }

        dataManager.saveAll(samples);
    }

}
