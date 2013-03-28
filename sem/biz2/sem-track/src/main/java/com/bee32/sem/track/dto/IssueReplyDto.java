package com.bee32.sem.track.dto;

import com.bee32.sem.mail.dto.MessageDto;
import com.bee32.sem.track.entity.IssueReply;

public class IssueReplyDto
        extends MessageDto<IssueReply, IssueReplyDto> {

    private static final long serialVersionUID = 1L;

    private IssueDto issue;

    @Override
    protected void _marshal(IssueReply source) {
        super._marshal(source);
        issue = mref(IssueDto.class, source.getIssue());
    }

    @Override
    protected void _unmarshalTo(IssueReply target) {
        super._unmarshalTo(target);
        merge(target, "issue", issue);
    }

    public IssueDto getIssue() {
        return issue;
    }

    public void setIssue(IssueDto issue) {
        this.issue = issue;
    }

}
