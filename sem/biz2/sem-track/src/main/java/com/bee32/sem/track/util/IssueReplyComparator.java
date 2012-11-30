package com.bee32.sem.track.util;

import java.util.Comparator;
import java.util.Date;

import com.bee32.sem.track.entity.IssueReply;

public class IssueReplyComparator
        implements Comparator<IssueReply> {

    @Override
    public int compare(IssueReply o1, IssueReply o2) {
        Date d1 = o1.getCreatedDate();
        Date d2 = o2.getCreatedDate();
        return d1.compareTo(d2);
    }

}
