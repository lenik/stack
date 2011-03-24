package com.bee32.plover.arch;

import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.model.qualifier.Qualified;

@ComponentTemplate
@Transactional
@Lazy
public abstract class EnterpriseService
        extends Qualified {

    public EnterpriseService() {
        super();
    }

    public EnterpriseService(String serviceName) {
        super(serviceName);
    }

}
