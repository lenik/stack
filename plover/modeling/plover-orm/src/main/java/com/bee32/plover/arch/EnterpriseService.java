package com.bee32.plover.arch;

import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.inject.ComponentTemplate;

@Transactional(readOnly = true)
@ComponentTemplate
@Lazy
public abstract class EnterpriseService
        extends Component {

    public EnterpriseService() {
        super();
    }

    public EnterpriseService(String serviceName) {
        super(serviceName);
    }

}
