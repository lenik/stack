package com.bee32.plover.indexing;

/**
 * 为索引程序。
 *
 */
public interface IIndexable {

    IIndexingMetaData getIndexingMetaData();

    void accept(IIndexingVisitor indexer);

}
