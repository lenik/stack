package com.bee32.plover.arch;

public interface IAppProfile
        extends IComponent {

    Object getParameter(Class<?> feature, String key);

}
