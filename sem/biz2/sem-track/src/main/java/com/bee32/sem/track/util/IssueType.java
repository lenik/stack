package com.bee32.sem.track.util;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;

/**
 * 事件类型
 *
 * <p lang="en">
 * Issue Type
 */
public class IssueType
        extends EnumAlt<Character, IssueType> {

    private static final long serialVersionUID = 1L;

    public IssueType(char value, String name) {
        super(value, name);
    }

    public static IssueType forName(String name) {
        return forName(IssueType.class, name);
    }

    public static Collection<IssueType> values() {
        return values(IssueType.class);
    }

    public static IssueType forValue(char value) {
        return forValue(IssueType.class, value);
    }

    /**
     * 问题
     *
     * <p lang="en">
     * Issue
     */
    public static final IssueType ISSUE = new IssueType('I', "issue");

    /**
     * 任务
     *
     * <p lang="en">
     * Task
     */
    public static final IssueType TASK = new IssueType('T', "task");

    /**
     * 请求
     *
     * <p lang="en">
     * Request
     */
    public static final IssueType REQUEST = new IssueType('R', "request");

}