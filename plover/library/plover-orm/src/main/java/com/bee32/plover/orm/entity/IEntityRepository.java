package com.bee32.plover.orm.entity;

import java.util.Collection;

import com.bee32.plover.arch.IRepository;
import com.bee32.plover.arch.naming.INamedNode;

public interface IEntityRepository<E extends IEntity<K>, K>
        extends IRepository<K, E>, INamedNode {

    /**
     * Or the storage class.
     */
    Class<? extends E> getEntityType();

    // Override following methods to get eclipse "generate override methods"
    // to work.

    @Override
    K getKey(E entity);

    @Override
    boolean contains(Object entity);

    @Override
    K save(E entity);

    @Override
    void update(E entity);

    @Override
    void refresh(E entity);

    @Override
    K saveOrUpdate(E entity);

    @Override
    void delete(Object entity);

    @Override
    boolean hasChild(Object entity);

    @Override
    String getChildName(Object entity);

    /**
     * 获取不参与持久层的样本实例。
     * <p>
     * 这些样本实例可用于：
     * <ol>
     * <li>构造单元测试。
     * <li>用于系统演示。
     * <li>用样本实例作为系统的初始数据。
     * </ol>
     *
     * <p>
     * 这里返回的实例应该是用程序精心构造的领域对象，而不是从持久层获得的实际实例。
     * <p>
     * 本 Repository 的 {@link #list()}、{@link #retrieve(Object)} 等方法不必要涉及本集合。
     *
     * @param worseCase
     *            对样本实例的择取。
     *            <p>
     *            考虑到单元测试和演示目的的不同用途，构造不同的样本集合。
     *            <p>
     *            如果 <code>worseCase</code>为 <code>true</code>
     *            时，应该返回一些奇怪的、诡异的、甚至内容错误的实例，因为单元测试需要一些苛刻的条件来检验系统的充分性。
     *            <p>
     *            当 <code>worseCase</code> 为 <code>false</code> 时，应该返回一些比较正常的、面向实际的样本集合。
     * @return 选定的非空的样本集合。
     */
    Collection<E> getTransientSamples(boolean worseCase);

}
