package com.bee32.sem.track.entity;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.IPriority;

public class IssueState
        extends EnumAlt<Character, IssueState>
        implements IPriority {

    private static final long serialVersionUID = 1L;

    static final int FINAL = 1;
    static final int NEGATIVE = 2;

    private final int priority;
    private final boolean _final;
    private final boolean negative;

    public IssueState(char value, String name, int priority, int flags) {
        super(value, name);
        this.priority = priority;
        _final = (flags & FINAL) != 0;
        negative = (flags & NEGATIVE) != 0;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public boolean isFinal() {
        return _final;
    }

    public boolean isNegative() {
        return negative;
    }

    public static IssueState forName(String name) {
        return forName(IssueState.class, name);
    }

    public static Collection<IssueState> values() {
        return values(IssueState.class);
    }

    public static IssueState forValue(Character value) {
        return forValue(IssueState.class, value);
    }

    public static IssueState forValue(char value) {
        return forValue(new Character(value));
    }

    /** 新创建的问题 */
    public static final IssueState NEW = new IssueState('N', "new", 0, 0);

    /** 问题已被接受，正在分配资源中 */
    public static final IssueState ACCEPTED = new IssueState('a', "accepted", 1, 0);

    /** 已开始处理问题 */
    public static final IssueState STARTED = new IssueState('s', "started", 2, 0);

    /** 缺陷已经被修复 */
    public static final IssueState FIXED = new IssueState('F', "fixed", 10, FINAL);

    /** 问题已经通过其它途径解决 */
    public static final IssueState DONE = new IssueState('V', "done", 11, FINAL);

    /** 问题已关闭，没有具体的原因 */
    public static final IssueState CLOSED = new IssueState('-', "closed", 12, FINAL);

    /** 问题已被搁置 */
    public static final IssueState PENDING = new IssueState('p', "pending", 3, 0);

    /** 重复的问题 */
    public static final IssueState DUP = new IssueState('D', "dup", 30, FINAL | NEGATIVE);

    /** 问题是有效的，但不会被考虑和解决 */
    public static final IssueState WONT_FIX = new IssueState('Y', "wontfix", 31, FINAL | NEGATIVE);

    /** 无效的问题 */
    public static final IssueState INVALID = new IssueState('X', "invalid", 32, FINAL | NEGATIVE);

}