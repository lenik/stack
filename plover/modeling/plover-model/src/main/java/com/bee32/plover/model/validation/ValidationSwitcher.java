package com.bee32.plover.model.validation;

import com.bee32.plover.inject.ServiceTemplate;

@ServiceTemplate
public abstract class ValidationSwitcher
        implements IValidationSwitcher {

    @Override
    public int getPriority() {
        return 0;
    }

}
