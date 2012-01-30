package com.bee32.plover.orm.upgrader;

import java.util.Date;

import com.bee32.plover.orm.upgrader.impl.PloverConfDatabaseMetadata;

/**
 * @see PloverConfDatabaseMetadata
 */
public interface IDatabaseMetadata {

    int getVersion();

    Date getLastUpgradedDate();

}
