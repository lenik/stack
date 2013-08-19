package com.bee32.sem.track.util;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.IPriority;

/**
 * 事件状态
 *
 * <p lang="en">
 * Issue State
 */
public class IssueState
        extends EnumAlt<Integer, IssueState>
        implements IPriority {

    private static final long serialVersionUID = 1L;

    static final int FINAL = 1;
    static final int NEGATIVE = 2;

    private final int priority;
    private final boolean _final;
    private final boolean negative;

    public IssueState(int value, String name, int priority, int flags) {
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

    public static IssueState forValue(int value) {
        return forValue(IssueState.class, value);
    }

    /**
     * 新建
     *
     * 新创建的事件
     *
     * <p * lang="en">
     * New Created
     */
    public static final IssueState NEW = new IssueState(0, "new", 0, 0);

    /**
     * 已接受
     *
     * 事件已被接受，正在分配资源中
     *
     * <p * lang="en">
     * Accepted
     */
    public static final IssueState ACCEPTED = new IssueState('a', "accepted", 1, 0);

    /**
     * 已开始
     *
     * 已开始处理事件
     *
     * <p * * lang="en">
     * Started
     */
    public static final IssueState STARTED = new IssueState('s', "started", 2, 0);

    /**
     * 已修正
     *
     * 缺陷已经被修复
     *
     * <p * lang="en">
     * Fixed
     */
    public static final IssueState FIXED = new IssueState('F', "fixed", 10, FINAL);

    /**
     * 已完成
     *
     * 事件已经通过其它途径解决
     *
     * <p * * lang="en">
     * Done
     */
    public static final IssueState DONE = new IssueState('V', "done", 11, FINAL);

    /**
     * 已关闭
     *
     * 事件已关闭，没有具体的原因
     *
     * <p * lang="en">
     * Closed
     */
    public static final IssueState CLOSED = new IssueState('-', "closed", 12, FINAL);

    /**
     * 暂停处理
     *
     * 事件已被搁置
     *
     * <p * lang="en">
     * Pending
     */
    public static final IssueState PENDING = new IssueState('p', "pending", 3, 0);

    /**
     * 存在重复
     *
     * 重复的事件
     *
     * <p * lang="en">
     * Duplicated
     */
    public static final IssueState DUP = new IssueState('D', "dup", 30, FINAL | NEGATIVE);

    /**
     * 不处理
     *
     * 事件是有效的，但不会被考虑和解决
     *
     * <p * lang="en">
     * Won't Fix
     */
    public static final IssueState WONT_FIX = new IssueState('Y', "wontfix", 31, FINAL | NEGATIVE);

    /**
     * 无效
     *
     * 无效的事件
     *
     * <p * lang="en">
     * Invalid
     */
    public static final IssueState INVALID = new IssueState('X', "invalid", 32, FINAL | NEGATIVE);

}