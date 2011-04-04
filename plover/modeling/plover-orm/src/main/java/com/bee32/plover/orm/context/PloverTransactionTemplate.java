package com.bee32.plover.orm.context;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@Lazy
public class PloverTransactionTemplate
        extends TransactionTemplate {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(PloverTransactionTemplate.class);

    public PloverTransactionTemplate() {
        logger.info("Create plover transaction template");
    }

    @Inject
    @Override
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        super.setTransactionManager(transactionManager);
    }

}
