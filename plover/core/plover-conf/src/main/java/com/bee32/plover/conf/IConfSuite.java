package com.bee32.plover.conf;

import java.util.Collection;

public interface IConfSuite
        extends IConf {

    <C extends IConf> C getSection(Class<C> confType);

    Collection<String> getSectionNames();

    Class<? extends IConf> getSectionType(String sectionName);

    IConf getSection(String sectionName);

    /**
     *
     */
    @Override
    Collection<String> getKeys();

    @Override
    Object get(String key);

}
