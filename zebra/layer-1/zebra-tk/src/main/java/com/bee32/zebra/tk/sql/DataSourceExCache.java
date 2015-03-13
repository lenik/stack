package com.bee32.zebra.tk.sql;

import java.util.HashMap;
import java.util.Map;

import net.bodz.bas.db.jdbc.DataSourceArguments;

public class DataSourceExCache {

    private Map<DataSourceArguments, DataSourceEx> map;

    public DataSourceExCache() {
        map = new HashMap<>();
    }

    public synchronized DataSourceEx get(DataSourceArguments args) {
        if (args == null)
            throw new NullPointerException("args");
        DataSourceEx cached = map.get(args);
        if (cached == null) {
            cached = new DataSourceEx(args);
            map.put(args, cached);
        }
        return cached;
    }

    public void remove(DataSourceArguments args) {
        map.remove(args);
    }

    public void clear() {
        map.clear();
    }

    static DataSourceExCache instance = new DataSourceExCache();

    public static DataSourceExCache getInstance() {
        return instance;
    }

}
