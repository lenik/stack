package com.bee32.zebra.oa.hr;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import net.bodz.bas.c.loader.ClassResource;
import net.bodz.bas.io.res.builtin.URLResource;
import net.bodz.bas.io.res.tools.StreamReading;

/**
 * 职称字典类
 * 
 * 定义了职称相关的词汇。
 * 
 * <p lang="en">
 * Job TItle
 */
public class JobTitle {

    private String code;
    private String text;

    private JobTitle(String code, String text) {
        this.code = code;
        this.text = text;
        codeMap.put(code, this);
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + " - " + text;
    }

    private static Map<String, JobTitle> codeMap = new LinkedHashMap<>();

    public static JobTitle forCode(String code) {
        return codeMap.get(code);
    }

    private static Map<String, JobTitleGroup> groupMap = new LinkedHashMap<>();

    public static Collection<JobTitleGroup> getGroups() {
        return groupMap.values();
    }

    public static JobTitleGroup forGroup(String prefix) {
        return groupMap.get(prefix);
    }

    static {
        reload();
    }

    static void reload() {
        groupMap.clear();
        codeMap.clear();
        JobTitleGroup group = null;

        URLResource csv = ClassResource.getData(JobTitle.class, "csv");
        for (String line : csv.to(StreamReading.class).lines(true)) {
            if (line.isEmpty())
                continue;
            int comma = line.indexOf(',');
            String code = line.substring(0, comma);
            String text = line.substring(comma + 1);
            if (code.length() < 3) {
                group = new JobTitleGroup(code, text);
                groupMap.put(code, group);
            } else {
                JobTitle title = new JobTitle(code, text);
                group.getItems().add(title);
            }
        } // for line
    }

}