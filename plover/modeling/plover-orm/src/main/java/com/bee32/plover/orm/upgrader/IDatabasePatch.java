package com.bee32.plover.orm.upgrader;

import org.hibernate.dialect.Dialect;

public interface IDatabasePatch {

    int getVersion();

    void install(Dialect dialect)
            throws Exception;

}
