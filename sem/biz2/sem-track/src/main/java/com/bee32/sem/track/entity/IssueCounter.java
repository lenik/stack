package com.bee32.sem.track.entity;

import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import com.bee32.plover.orm.entity.EntityAuto;

public class IssueCounter
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Issue issue;
    int readCount;
    int updateCount;
    int replyCount;
    int downloadCount;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        if (issue == null)
            throw new NullPointerException("issue");
        this.issue = issue;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

}
