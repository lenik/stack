package com.bee32.plover.internet;

import com.bee32.plover.arch.IComponent;
import com.bee32.plover.arch.ui.Appearance;
import com.bee32.plover.arch.ui.IAppearance;
import com.bee32.plover.arch.util.ExceptionSupport;

public class InstanceComponent
        extends Appearance
        implements IComponent {

    public InstanceComponent(Class<?> declaringType) {
        super(declaringType);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public IAppearance getAppearance() {
        return null;
    }

    @Override
    public ExceptionSupport getExceptionSupport() {
        return null;
    }

}
