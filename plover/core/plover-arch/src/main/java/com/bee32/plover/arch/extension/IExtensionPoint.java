package com.bee32.plover.arch.extension;

import com.bee32.plover.arch.IComponent;

public interface IExtensionPoint
        extends IComponent {

    /**
     * Get the extension point name.
     */
    @Override
    String getName();

}
