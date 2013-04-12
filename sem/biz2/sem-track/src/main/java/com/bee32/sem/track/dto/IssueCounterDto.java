package com.bee32.sem.track.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.track.entity.IssueCounter;

public class IssueCounterDto
        extends EntityDto<IssueCounter, Long> {

    private static final long serialVersionUID = 1L;

    public static final int ISSUE_REF = 1;

    private IssueDto issue;
    private int readCount;
    private int updateCount;
    private int replyCount;
    private int downloadCount;

    @Override
    protected void _marshal(IssueCounter source) {
        if (selection.contains(ISSUE_REF))
            issue = mref(IssueDto.class, source.getIssue());

        readCount = source.getReadCount();
        updateCount = source.getUpdateCount();
        replyCount = source.getReplyCount();
        downloadCount = source.getDownloadCount();
    }

    @Override
    protected void _unmarshalTo(IssueCounter target) {
        if (selection.contains(ISSUE_REF))
            merge(target, "issue", issue);
        target.setReadCount(readCount);
        target.setUpdateCount(updateCount);
        target.setReplyCount(replyCount);
        target.setDownloadCount(downloadCount);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public IssueDto getIssue() {
        return issue;
    }

    public void setIssue(IssueDto issue) {
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
