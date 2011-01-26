package com.bee32.plover.inject;

import javax.free.IExecutableX;

/**
 * Plover 容器对象。
 */
public interface IContainer {

    /**
     * 向容器请求必须的上下文，容器必须返回非空值。
     *
     * @param contextClass
     *            上下文实例的类型
     * @return 组件自行注入的上下问实例，非空值。
     * @throws NoSuchContextException
     *             容器中没有定义这个上下文类。
     * @throws InstantiateContextException
     *             容器无法实例化该指定的上下文类。
     */
    <T> T require(Class<T> contextClass)
            throws ContextException;

    /**
     * 向容器请求必须的上下文，容器必须返回非空值。
     *
     * @param contextClass
     *            上下文实例的类型
     * @param qualifier
     *            修饰语，用于区分同名类的不同实例。
     * @return 组件自行注入的上下问实例，非空值。
     * @throws NoSuchContextException
     *             容器中没有定义这个上下文类。
     * @throws InstantiateContextException
     *             容器无法实例化该指定的上下文类。
     */
    <T> T require(Class<T> contextClass, Object qualifier)
            throws ContextException;

    /**
     * 向容器请求必须的上下文，容器必须返回非空值。
     *
     * @param contextClass
     *            上下文实例的类型
     * @param qualifier
     *            修饰语，用于区分同名类的不同实例。
     * @return 组件自行注入的上下问实例，非空值。
     * @throws NoSuchContextException
     *             容器中没有定义这个上下文类。
     * @throws InstantiateContextException
     *             容器无法实例化该指定的上下文类。
     */
    <T> T require(Class<T> contextClass, String qualifier)
            throws ContextException;

    /**
     * 向容器请求可选的上下文，容器可以返回非空值。
     *
     * @param contextClass
     *            上下文实例的类型
     * @return 组件自行注入的上下问实例，非空值。
     * @throws InstantiateContextException
     *             容器无法实例化该指定的上下文类。
     */
    <T> T query(Class<T> contextClass)
            throws ContextException;

    /**
     * 向容器请求可选的上下文，容器可以返回非空值。
     *
     * @param contextClass
     *            上下文实例的类型
     * @param qualifier
     *            修饰语，用于区分同名类的不同实例。
     * @return 组件自行注入的上下问实例，非空值。
     * @throws InstantiateContextException
     *             容器无法实例化该指定的上下文类。
     */
    <T> T query(Class<T> contextClass, Object qualifier)
            throws ContextException;

    /**
     * 向容器请求可选的上下文，容器可以返回非空值。
     *
     * @param contextClass
     *            上下文实例的类型
     * @param qualifier
     *            修饰语，用于区分同名类的不同实例。
     * @return 组件自行注入的上下问实例，非空值。
     * @throws InstantiateContextException
     *             容器无法实例化该指定的上下文类。
     */
    <T> T query(Class<T> contextClass, String qualifier)
            throws ContextException;

    /**
     * 向容器注册上下文实例。
     *
     * @param contextClass
     *            上下文实例的类型，非空值
     * @param qualifier
     *            修饰语，用于区分同名类的不同实例。
     * @param contextInstance
     *            上下文实例，非空值
     */
    <T> void registerContext(Class<T> contextClass, T contextInstance);

    /**
     * 向容器注册上下文实例。
     *
     * @param contextClass
     *            上下文实例的类型，非空值
     * @param qualifier
     *            修饰语，用于区分同名类的不同实例。
     * @param contextInstance
     *            上下文实例，非空值
     */
    <T> void registerContext(Class<T> contextClass, String qualifier, T contextInstance);

    /**
     * 向容器注册上下文实例。
     *
     * @param contextClass
     *            上下文实例的类型，非空值
     * @param qualifier
     *            修饰语，用于区分同名类的不同实例。
     * @param contextInstance
     *            上下文实例，非空值
     */
    <T> void registerContext(Class<T> contextClass, Object qualifier, T contextInstance);

    /**
     * 从容器中移除上下文实例。
     *
     * @param contextClass
     *            上下文实例的类型，非空值
     * @param qualifier
     *            修饰语，用于区分同名类的不同实例。
     */
    void removeContext(Class<?> contextClass, String qualifier);

    /**
     * 从容器中移除上下文实例。
     *
     * @param contextClass
     *            上下文实例的类型，非空值
     * @param qualifier
     *            修饰语，用于区分同名类的不同实例。
     */
    void removeContext(Class<?> contextClass, Object qualifier);

    /**
     * 从容器中移除所有相同的上下文实例。
     *
     * @param contextInstance
     *            要移除的上下文实例
     */
    void removeContextInstances(Object contextInstance);

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

    <X extends Exception> void execute(IExecutableX<X> closure)
            throws X;

}
