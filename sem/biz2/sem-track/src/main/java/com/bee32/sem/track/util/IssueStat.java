package com.bee32.sem.track.util;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import com.bee32.icsf.login.SessionUser;
import com.bee32.sem.track.entity.Issue;
import com.bee32.sem.track.entity.IssueObserver;

public class IssueStat
        implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * User id -> UserIssueStat
     */
    private Map<Integer, UserIssueStat> userMap = new TreeMap<Integer, UserIssueStat>();

    public Map<Integer, UserIssueStat> getUserMap() {
        return userMap;
    }

    public synchronized UserIssueStat getUserStat(int userId) {
        UserIssueStat userStat = userMap.get(userId);
        if (userStat == null) {
            userStat = new UserIssueStat();
            userMap.put(userId, userStat);
        }
        return userStat;
    }

    public UserIssueStat getCurrentUserStat() {
        Integer userId = SessionUser.getInstance().getId();
        if (userId == null)
            throw new IllegalStateException("Not logged in.");
        UserIssueStat userStat = getUserStat(userId);
        return userStat;
    }

    public void updateIssue(Issue issue) {
        long issueId = issue.getId();
        int ownerId = issue.getOwner().getId();
        UserIssueStat userStat = getUserStat(ownerId);
        userStat.setRead(issueId, true);

        for (IssueObserver issueObserver : issue.getObservers()) {
            long obsIssueId = issueObserver.getIssue().getId();
            int obsUserId = issueObserver.getObserver().getId();

            UserIssueStat obsUserStat = getUserStat(obsUserId);
            obsUserStat.setRead(obsIssueId, issueObserver.isRead());
        }
    }

}
