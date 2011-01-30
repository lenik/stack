package com.bee32.plover.model.stage;

import com.bee32.plover.model.qualifier.Group;

public class GroupElement
        extends StagedElement {

    public GroupElement(Group group) {
        super(group.getName());
        // addQualifier(group);
    }

}
