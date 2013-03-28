package com.bee32.sem.track.web;

import com.bee32.sem.track.util.TrackCriteria;

public class ObservedIssueView
        extends AbstractIssueView {

    private static final long serialVersionUID = 1L;

    public ObservedIssueView() {
        super(TrackCriteria.observedByCurrentUser());
    }

}
