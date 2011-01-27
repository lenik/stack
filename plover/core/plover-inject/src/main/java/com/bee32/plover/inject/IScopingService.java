package com.bee32.plover.inject;

public interface IScopingService {

    /**
     * 获得当前栈帧。
     *
     * 这个方法只在组件进入/离开容器时有效。容器在实现本方法时应该提供临界机制以避免并发问题。
     *
     * 栈帧只是容器在调用组件时分配的一个存储槽，组件可以利用这个槽来保存环境，以便离开容器时能够正确恢复到之前的状态。
     * <p>
     * 实际上，组件常常需要更复杂的存储空间来保存环境，并在后续的方法中逐渐的保存更多的东西。
     *
     * 比如一个对象维护某个数组，只有当对该数组具体的元素写入时才需要保存之前的值，这个时候使用 {@link #getFrameAttribute(Object)}/
     * {@link #setFrameAttribute(Object, Object)} 可能更方便。这两个方法实际上自动分配一个堆上的 HashMap，而在栈帧上存放到该 HashMap
     * 的引用。
     */
    Object getFrame();

    /**
     * 设置当前栈帧。
     *
     * 这个方法只在组件进入/离开容器时有效。容器在实现本方法时应该提供临界机制以避免并发问题。
     *
     * 栈帧只是容器在调用组件时分配的一个存储槽，组件可以利用这个槽来保存环境，以便离开容器时能够正确恢复到之前的状态。
     * <p>
     * 实际上，组件常常需要更复杂的存储空间来保存环境，并在后续的方法中逐渐的保存更多的东西。
     *
     * 比如一个对象维护某个数组，只有当对该数组具体的元素写入时才需要保存之前的值，这个时候使用 {@link #getFrameAttribute(Object)}/
     * {@link #setFrameAttribute(Object, Object)} 可能更方便。这两个方法实际上自动分配一个堆上的 HashMap，而在栈帧上存放到该 HashMap
     * 的引用。
     */
    void setFrame(Object frameObject);

    Object getFrameAttribute(Object key);

    void setFrameAttribute(Object key, Object value);

}
