package com.bee32.plover.faces.view;

import com.bee32.plover.model.validation.ValidationSwitcher;

public class DefaultVs
        extends ValidationSwitcher {

    public static boolean DEFAULT_ENABLED = false;

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Boolean isValidationEnabled() {
        return DEFAULT_ENABLED;
    }

}
