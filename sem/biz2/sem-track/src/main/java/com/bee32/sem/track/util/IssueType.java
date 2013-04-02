package com.bee32.sem.track.util;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;

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

    public static final IssueType ISSUE = new IssueType('I', "issue");
    public static final IssueType TASK = new IssueType('T', "task");
    public static final IssueType REQUEST = new IssueType('R', "request");

}