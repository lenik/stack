package com.bee32.sem.makebiz.util;

public class ErrorInfoModel {

    int count;
    String label;
    String info;

    public ErrorInfoModel(int count, String label, String info) {
        this.count = count;
        this.label = label;
        this.info = info;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
