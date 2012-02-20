package com.bee32.plover.sql;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SQLTrackDB {

    int cacheSize = 100;
    LinkedList<SQLRecord> recentSqls = new LinkedList<SQLRecord>();

    public SQLTrackDB() {
    }

    public void addSql(SQLRecord sql) {
        recentSqls.addFirst(sql);
        while (recentSqls.size() > cacheSize)
            recentSqls.removeLast();
    }

    public LinkedList<SQLRecord> getRecentSqls() {
        return recentSqls;
    }

    static Map<Object, SQLTrackDB> sessionMap = new HashMap<Object, SQLTrackDB>();

    public static Map<Object, SQLTrackDB> getSessionMap() {
        return sessionMap;
    }

    public static SQLTrackDB getInstance(Object sessionKey) {
        SQLTrackDB db = sessionMap.get(sessionKey);
        if (db == null) {
            synchronized (sessionMap) {
                if (db == null) {
                    db = new SQLTrackDB();
                    sessionMap.put(sessionKey, db);
                }
            }
        }
        return db;
    }

}
