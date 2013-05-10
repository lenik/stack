package com.bee32.sem.track.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserIssueStat
        implements Serializable {

    private static final long serialVersionUID = 1L;

    static final int READ = 1;

    private Map<Long, Integer> issueFlagsMap = new HashMap<Long, Integer>();

    public int getIssueFlags(long issueId) {
        Integer flags = issueFlagsMap.get(issueId);
        if (flags == null)
            return 0;
        else
            return flags;
    }

    public void setIssueFlags(long issueId, int flags) {
        issueFlagsMap.put(issueId, flags);
    }

    public boolean isRead(long issueId) {
        int flags = getIssueFlags(issueId);
        return (flags & READ) != 0;
    }

    public void setRead(long issueId, boolean read) {
        int flags = getIssueFlags(issueId);
        if (read)
            flags |= READ;
        else
            flags &= ~READ;
        setIssueFlags(issueId, flags);
    }

    public int getIssueCount() {
        return issueFlagsMap.size();
    }

    public int getUnreadCount() {
        int count = 0;
        for (Integer flags : issueFlagsMap.values()) {
            int _flags = flags;
            if ((_flags & READ) == 0)
                count++;
        }
        return count;
    }

}