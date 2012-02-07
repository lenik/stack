package com.bee32.plover.sql;

import java.io.Serializable;

public class SQLRecord
        implements Serializable {

    private static final long serialVersionUID = 1L;

    int connectionId;
    long time;
    long elapsed;
    String category;
    String prepared;
    String sql;

    public SQLRecord(int connectionId, long time, long elapsed, String category, String prepared, String sql) {
        this.connectionId = connectionId;
        this.time = time;
        this.elapsed = elapsed;
        this.category = category;
        this.prepared = prepared;
        this.sql = sql;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getElapsed() {
        return elapsed;
    }

    public void setElapsed(long elapsed) {
        this.elapsed = elapsed;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrepared() {
        return prepared;
    }

    public void setPrepared(String prepared) {
        this.prepared = prepared;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

}
