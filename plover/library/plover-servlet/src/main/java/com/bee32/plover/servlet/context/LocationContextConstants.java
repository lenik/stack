package com.bee32.plover.servlet.context;

public interface LocationContextConstants {

    LocationContext REQUEST = new LocationContext("request");
    LocationContext WEB_APP = new LocationContext("web-app");

    LocationContext URL = new LocationContext(null);
    LocationContext JAVASCRIPT = new LocationContext("javascript://");

}
