package com.bee32.zebra.oa.hr;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import net.bodz.bas.c.loader.ClassResource;
import net.bodz.bas.io.res.builtin.URLResource;
import net.bodz.bas.io.res.tools.StreamReading;

/**
 * 岗位字典类
 * 
 * 定义了公司中存在的各种工作岗位。
 * 
 * <p lang="en">
 * Job Position
 */
public class JobPosition {

    private String code;
    private String text;
    private boolean category;

    private JobPosition(String code, String text, boolean category) {
        this.code = code;
        this.text = text;
        this.category = category;
        codeMap.put(code, this);
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public boolean isCategory() {
        return category;
    }

    @Override
    public String toString() {
        if (category)
            return "== " + text + " ==";
        else
            return code + " - " + text;
    }

    private static Map<String, JobPosition> codeMap = new LinkedHashMap<>();

    public static JobPosition forCode(String code) {
        return codeMap.get(code);
    }

    private static Map<Integer, JobPositionGroup> groupMap = new LinkedHashMap<>();

    public static Collection<JobPositionGroup> getGroups() {
        return groupMap.values();
    }

    public static JobPositionGroup forGroup(int groupId) {
        return groupMap.get(groupId);
    }

    static {
        reload();
    }

    static void reload() {
        groupMap.clear();
        codeMap.clear();
        JobPositionGroup group = null;
        int groupId = 0;

        URLResource csv = ClassResource.getData(JobPosition.class, "csv");
        for (String line : csv.to(StreamReading.class).lines(true)) {
            if (line.isEmpty())
                continue;
            int comma = line.indexOf(',');
            if (comma == -1) {
                groupId++;
                group = new JobPositionGroup(groupId, line);
                groupMap.put(groupId, group);
            } else {
                String code = line.substring(0, comma);
                String text = line.substring(comma + 1);
                boolean category = code.length() < 6;
                JobPosition position = new JobPosition(code, text, category);
                group.getItems().add(position);
            }
        } // for line
    }

}
