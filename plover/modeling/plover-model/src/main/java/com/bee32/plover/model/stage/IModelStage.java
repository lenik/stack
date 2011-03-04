package com.bee32.plover.model.stage;

import java.io.Serializable;

import com.bee32.plover.inject.IContainer;
import com.bee32.plover.model.profile.Profile;
import com.bee32.plover.model.qualifier.IQualified;

public interface IModelStage
        extends IContainer, IQualified, Iterable<IStagedElement>, Serializable {

    /**
     * Get the preferred view to stage.
     *
     * @return Non-<code>null</code> view qualifier.
     */
    Profile getProfile();

    /**
     * Add the element to the staged index.
     *
     * @param element
     *            The element to add.
     */
    void add(IStagedElement element);

    // void add(Collection<? extends IStagedElement> elements);

    /**
     * Remove the element from the staged index.
     *
     * @param element
     *            The element to remove.
     */
    void remove(IStagedElement element);

}
