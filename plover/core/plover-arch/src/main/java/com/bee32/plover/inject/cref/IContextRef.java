package com.bee32.plover.inject.cref;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

public interface IContextRef {

    Resource[] getConfigResources();

    void exportResources(List<Resource> resources);

    ApplicationContext buildApplicationContext();

    ApplicationContext buildApplicationContext(ApplicationContext parent);

}
