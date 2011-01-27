package com.bee32.plover.model.stage;

import com.bee32.plover.arch.IComponent;
import com.bee32.plover.model.qualifier.IQualified;

public interface IStagedElement
        extends IComponent, IQualified {

    Class<?> getElementType();

    Object getElementValue();

}
