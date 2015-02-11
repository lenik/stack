package com.bee32.zebra.oa.hr;

import java.util.ArrayList;
import java.util.List;

/**
 * 岗位组
 */
public class JobPositionGroup {

    int id;
    String text;
    List<JobPosition> items = new ArrayList<JobPosition>();

    public JobPositionGroup(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<JobPosition> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return id + " - " + text;
    }

}
