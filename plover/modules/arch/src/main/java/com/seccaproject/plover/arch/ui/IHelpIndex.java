package com.seccaproject.plover.arch.ui;

import java.util.Collection;

public interface IHelpIndex {

    /**
     * @return non-<code>null</code> array of tag names in use.
     */
    String[] getTags();

    /**
     * @return non-<code>null</code> {@link Collection} of help entries matched for the specified
     *         tag name
     */
    Collection<? extends IHelpEntry> getEntries(String tag);

    /**
     * @return <code>null</code> if no help available for the specified <code>tag</code>.
     */
    IHelpEntry getPreferredEntry(String tag);

}
