package com.seccaproject.plover.arch.ui;

import java.util.Collection;

/**
 * 参考、帮助信息
 */
public interface IRefdocs {

    /**
     * @return non-<code>null</code> array of tag names in use.
     */
    String[] getTags();

    /**
     * @return non-<code>null</code> {@link Collection} of {@link IRefdocEntry}s for the specified
     *         <code>tag</code>
     */
    Collection<? extends IRefdocEntry> getEntries(String tag);

    /**
     * @return <code>null</code> if no refdoc available for the specified <code>tag</code>.
     */
    IRefdocEntry getDefaultEntry(String tag);

}
