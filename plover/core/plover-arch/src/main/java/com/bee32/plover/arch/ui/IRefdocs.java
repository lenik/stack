package com.bee32.plover.arch.ui;

import java.util.Collection;
import java.util.Set;

/**
 * 本接口用于提供参考、帮助信息。
 */
public interface IRefdocs {

    int traitsIndex = 1125825733; // IRefdocs

    /**
     * 获取文档中使用了的标签集。
     *
     * 这些标签可用于文档的分类、搜索。
     *
     * @return non-<code>null</code> array of tag names in use.
     */
    Set<String> getTags();

    /**
     * 获取指定标签下的条目列表。
     *
     * @return non-<code>null</code> {@link Collection} of {@link IRefdocEntry}s for the specified
     *         <code>tag</code>
     */
    Iterable<? extends IRefdocEntry> getEntries(String tag);

    /**
     * 获取指定标签下的缺省条目。
     *
     * @return <code>null</code> if no refdoc available for the specified <code>tag</code>.
     */
    IRefdocEntry getDefaultEntry(String tag);

}
