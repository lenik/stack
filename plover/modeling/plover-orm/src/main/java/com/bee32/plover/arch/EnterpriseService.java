package com.bee32.plover.arch;

import org.springframework.context.annotation.Lazy;

import com.bee32.plover.inject.ComponentTemplate;

@Lazy
@ComponentTemplate
// @Transactional(readOnly = true)
public abstract class EnterpriseService
        extends Component {

    public EnterpriseService() {
        super();
    }

    public EnterpriseService(String serviceName) {
        super(serviceName);
    }

}
