package com.bee32.plover.arch;

import org.springframework.context.annotation.Lazy;

import com.bee32.plover.inject.ComponentTemplate;

@ComponentTemplate
// @Transactional
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
