package com.bee32.plover.conf;

import javax.free.IContext;

public interface IConfManager {

    <T> T getConf(Class<T> confType, IContext context);

}
