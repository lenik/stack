package com.bee32.sem.track.util;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.sem.track.entity.Issue;
import com.bee32.sem.track.entity.IssueCounter;

public class IssueService
        extends DataService {

    @Transactional(readOnly = false)
    public void setCounter(long issueId, CounterType counterType, int count) {

        IssueCounter counter = DATA(IssueCounter.class).getUnique(//
                new Equals("issue.id", issueId));

        if (counter == null) {
            counter = new IssueCounter();
            counter.setIssue(DATA(Issue.class).lazyLoad(issueId));
        }

        switch (counterType) {
        case READ:
            counter.setReadCount(count);
            break;
        case UPDATE:
            counter.setUpdateCount(count);
            break;
        case REPLY:
            counter.setReplyCount(count);
            break;
        case DOWNLOAD:
            counter.setDownloadCount(count);
            break;
        }

        DATA(IssueCounter.class).saveOrUpdate(counter);
    }

    @Transactional(readOnly = false)
    public void addCounter(long issueId, CounterType counterType, int delta) {

        IssueCounter counter = DATA(IssueCounter.class).getUnique(//
                new Equals("issue.id", issueId));

        if (counter == null) {
            counter = new IssueCounter();
            counter.setIssue(DATA(Issue.class).lazyLoad(issueId));
        }

        int count = 0;

        switch (counterType) {
        case READ:
            count = counter.getReadCount();
            break;

        case UPDATE:
            count = counter.getUpdateCount();
            break;

        case REPLY:
            count = counter.getReplyCount();
            break;

        case DOWNLOAD:
            count = counter.getDownloadCount();
            break;
        }

        count += delta;

        switch (counterType) {
        case READ:
            counter.setReadCount(count);
            break;
        case UPDATE:
            counter.setUpdateCount(count);
            break;
        case REPLY:
            counter.setReplyCount(count);
            break;
        case DOWNLOAD:
            counter.setDownloadCount(count);
            break;
        }

        DATA(IssueCounter.class).saveOrUpdate(counter);
    }

}
