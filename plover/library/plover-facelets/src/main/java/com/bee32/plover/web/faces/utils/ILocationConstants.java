package com.bee32.plover.web.faces.utils;

public interface ILocationConstants {

    LocationsBean locations = new LocationsBean();

    LocationVmap JAVASCRIPT = locations.get("JAVASCRIPT");
    LocationVmap REQUEST = locations.get("REQUEST");
    LocationVmap URL = locations.get("URL");
    LocationVmap WEB_APP = locations.get("WEB_APP");
    LocationVmap LIB_JS = locations.get("LIB_JS");
    LocationVmap STYLE_ROOT = locations.get("STYLE_ROOT");
    LocationVmap STYLE = locations.get("STYLE");
    LocationVmap ICON = locations.get("ICON");
    LocationVmap SYMBOLS = locations.get("SYMBOLS");
    LocationVmap LIB_3RDPARTY = locations.get("LIB_3RDPARTY");

}
