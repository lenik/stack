package com.bee32.plover.indexing;

/**
 * 为索引程序。
 *
 */
public interface IIndexable {

    void accept(IIndexingVisitor indexer);

    IIndexingMetaData getIndexingMetaData();

}
