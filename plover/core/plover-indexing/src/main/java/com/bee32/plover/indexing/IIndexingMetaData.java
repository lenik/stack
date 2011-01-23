package com.bee32.plover.indexing;

/**
 * 索引元数据
 * <p>
 * 本接口定义了用于支持对象索引的类型参数。
 */
public interface IIndexingMetaData {

    /**
     * 获取索引键的数目。
     *
     * @return 索引键的数目 。
     */
    int getIndexKeyCount();

    /**
     * 获取指定索引键的名称。
     *
     * @param indexKeyIndex
     *            指定要获取的索引键的索引，基于<code>0</code>。
     * @return 指定索引键的名称，不为空值。
     * @throws IndexOutOfBoundsException
     *             如果 <code>indexKeyIndex</code>取值超出范围。
     */
    String getIndexKeyName(int indexKeyIndex);

    /**
     * 获取指定索引键。
     *
     * @param indexKeyIndex
     *            指定要获取的索引键的索引，基于<code>0</code>。
     * @return 指定索引键，不为空值。
     * @throws IndexOutOfBoundsException
     *             如果 <code>indexKeyIndex</code>取值超出范围。
     */
    IndexKey getIndexKey(int indexKeyIndex);

}
