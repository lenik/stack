package com.bee32.sem.track.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.ox1.color.Blue;

/**
 * 事件计数器
 *
 * 记录事件的阅读、下载、更新等次数。
 */
@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "issue_counter_seq", allocationSize = 1)
public class IssueCounter
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Issue issue;
    int readCount;
    int updateCount;
    int replyCount;
    int downloadCount;

    /**
     * 事件
     *
     * 相关的事件。
     */
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        if (issue == null)
            throw new NullPointerException("issue");
        this.issue = issue;
    }

    /**
     * 阅读次数
     *
     * 事件被阅读的次数。
     */
    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    /**
     * 更新次数
     *
     * 事件被更新的次数。
     */
    public int getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }

    /**
     * 回复次数
     *
     * 事件被回复的次数。
     */
    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    /**
     * 下载次数
     *
     * 事件的附件被下载的次数。
     */
    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

}
