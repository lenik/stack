package com.bee32.sem.track.util;

import javax.free.UnexpectedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.sem.track.entity.Issue;
import com.bee32.sem.track.entity.IssueCounter;
import com.bee32.sem.track.entity.IssueObserver;

public class IssueService
        extends DataService {

    static Logger logger = LoggerFactory.getLogger(IssueService.class);

    IssueStat issueStat;

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

        default:
            throw new UnexpectedException();
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

        default:
            throw new UnexpectedException();
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

        default:
            throw new UnexpectedException();
        }

        DATA(IssueCounter.class).saveOrUpdate(counter);
        // DATA(IssueCounter.class).evict(counter);
    }

    boolean forceRefresh = true;

    public IssueStat getIssueStat() {
        if (issueStat == null || forceRefresh) {
            synchronized (this) {
                if (issueStat == null || forceRefresh) {
                    issueStat = new IssueStat();

                    for (Issue issue : DATA(Issue.class).list())
                        issueStat.updateIssue(issue);
                }
            }
        }
        return issueStat;
    }

    @Transactional(readOnly = false)
    public void setReadFlag(long issueId, int userId, boolean read) {
        IssueObserver observer = DATA(IssueObserver.class).getUnique(//
                new Equals("issue.id", issueId), //
                new Equals("observer.id", userId));

        // user isn't an observer, do nothing.
        if (observer == null) {
            logger.warn("User isn't in observer list..");
            return;
        }

        observer.setRead(read);

        DATA(IssueObserver.class).update(observer);
    }

}
