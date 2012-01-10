package com.bee32.plover.orm.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.orm.util.ErrorResult;

public class TraceELA
        extends AbstractEntityLifecycleAddon {

    static Logger logger = LoggerFactory.getLogger(TraceELA.class);

    @Override
    public ErrorResult entityCreate(Entity<?> entity) {
        logger.info("create: " + entity.getEntryText());
        return ErrorResult.SUCCESS;
    }

    @Override
    public ErrorResult entityValidate(Entity<?> entity) {
        logger.info("validate: " + entity.getEntryText());
        return ErrorResult.SUCCESS;
    }

    @Override
    public ErrorResult entityCheckModify(Entity<?> entity) {
        logger.info("checkModify: " + entity.getEntryText());
        return ErrorResult.SUCCESS;
    }

    @Override
    public ErrorResult entityCheckDelete(Entity<?> entity) {
        logger.info("checkDelete: " + entity.getEntryText());
        return ErrorResult.SUCCESS;
    }

    @Override
    public void entityUpdated(Entity<?> entity) {
        logger.info("updated: " + entity.getEntryText());
    }

    @Override
    public void entityDeleted(Entity<?> entity) {
        logger.info("deleted: " + entity.getEntryText());
    }

}
