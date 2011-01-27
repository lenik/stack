package com.bee32.plover.inject;

import com.bee32.plover.arch.IComponent;

public interface IContextManager
        extends IComponent {

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
     */
    void removeContext(Class<?> contextClass);

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

}
