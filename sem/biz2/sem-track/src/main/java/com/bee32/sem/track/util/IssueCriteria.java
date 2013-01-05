package com.bee32.sem.track.util;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public class IssueCriteria
        extends CriteriaSpec {

    public static ICriteriaElement getIssueByState(char stateValue) {
        return equals("_state", stateValue);
    }

    public static ICriteriaElement listIssueByObserver(int userId, String globalState, boolean flag) {

        Character stateValue = (globalState == null || globalState.isEmpty()) ? null : globalState.charAt(0);
        CriteriaElement alias = flag ? alias("observers", "target") : alias("favs", "target");
        CriteriaElement equalId = equals("target.user.id", userId);

        CriteriaElement equalState = stateValue == null ? null : equals("_state", stateValue);

        if (userId > 0)
            if (stateValue == null)
                return compose(alias, equalId);
            else
                return compose(alias, equalId, equalState);
        else
            return equalState;
    }

    CriteriaElement cusAlias(String aliaed) {
        return alias(aliaed, "target");
    }

    public static ICriteriaElement getUniqueById(long id) {
        return equals("id", id);
    }

    public static ICriteriaElement getObserverCount(long issueId) {
        return equals("issue.id", issueId);
    }

    public static ICriteriaElement getUniqueByIssueAndUser(long issueId, int userId) {
        return and(equals("issue.id", issueId), equals("user.id", userId));
    }
}
