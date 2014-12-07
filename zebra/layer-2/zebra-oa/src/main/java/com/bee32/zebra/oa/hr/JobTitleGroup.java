package com.bee32.zebra.oa.hr;

import java.util.ArrayList;
import java.util.List;

public class JobTitleGroup {

    String prefix;
    String text;
    List<JobTitle> items = new ArrayList<JobTitle>();

    public JobTitleGroup(String prefix, String text) {
        this.prefix = prefix;
        this.text = text;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getText() {
        return text;
    }

    public List<JobTitle> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return prefix + " - " + text;
    }

}
