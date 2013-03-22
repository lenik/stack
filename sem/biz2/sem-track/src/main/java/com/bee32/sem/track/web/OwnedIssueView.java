package com.bee32.sem.track.web;

import com.bee32.icsf.principal.UserCriteria;

public class OwnedIssueView
        extends AbstractIssueView {

    private static final long serialVersionUID = 1L;

    public OwnedIssueView() {
        super(UserCriteria.ownedByCurrentUser());
    }

}
