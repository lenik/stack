package com.bee32.sem.world.monetary;

import java.util.TimeZone;

public interface ITimeZoneAware {

    TimeZone TZ_DEFAULT = TimeZone.getDefault();
    TimeZone TZ_SHANGHAI = TimeZone.getTimeZone("PRC");

}
