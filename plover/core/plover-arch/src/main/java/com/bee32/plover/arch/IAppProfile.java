package com.bee32.plover.arch;

import java.util.Map;

public interface IAppProfile {

    Map<String, Object> getParameters(Class<?> feature);

}
