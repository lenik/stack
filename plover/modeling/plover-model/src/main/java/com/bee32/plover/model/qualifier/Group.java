package com.bee32.plover.model.qualifier;

import com.bee32.plover.arch.Component;

/**
 * 分组修饰语。
 * <p>
 * 用于对元素进行分组。
 *
 * 若多个分组同时存在，元素将根据各组的优先级而被归类为子组 。
 */
public class Group
        extends Qualifier<Group> {

    private static final long serialVersionUID = 1L;

    private int priority;

    public Group(String name, int priority) {
        super(Group.class, name);

        if (name == null)
            throw new NullPointerException("name");

        this.priority = priority;
    }

    /**
     * 获取组的优先级。
     *
     * 组优先级用于排序的时候区分首选字段还是次要字段。
     *
     * @return 优先级数值，数值越小、优先级越高。
     */
    @Override
    public int getPriority() {
        return priority;
    }

    /**
     * 设置组的优先级。
     *
     * 组优先级用于排序的时候区分首选字段还是次要字段。
     *
     * @param priority
     *            优先级数值，数值越小、优先级越高。
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int hashCodeSpecific() {
        int hash = priority;
        return hash;
    }

    @Override
    public boolean equalsSpecific(Component obj) {
        Group o = (Group) obj;

        if (priority != o.priority)
            return false;

        return true;
    }

}
