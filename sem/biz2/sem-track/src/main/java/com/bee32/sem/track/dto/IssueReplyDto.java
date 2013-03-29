package com.bee32.sem.track.dto;

import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.sem.mail.dto.MessageDto;
import com.bee32.sem.track.entity.IssueReply;

public class IssueReplyDto
        extends MessageDto<IssueReply, IssueReplyDto>
        implements IEnclosedObject<IssueDto> {

    private static final long serialVersionUID = 1L;

    public static final int ISSUE = 1;

    private IssueDto issue;
    private UserDto user;

    @Override
    public IssueDto getEnclosingObject() {
        return getIssue();
    }

    @Override
    public void setEnclosingObject(IssueDto enclosingObject) {
        setIssue(enclosingObject);
    }

    @Override
    protected void _marshal(IssueReply source) {
        super._marshal(source);

        if (selection.contains(ISSUE))
            issue = mref(IssueDto.class, source.getIssue());

        user = mref(UserDto.class, source.getUser());
    }

    @Override
    protected void _unmarshalTo(IssueReply target) {
        super._unmarshalTo(target);

        if (selection.contains(ISSUE))
            merge(target, "issue", issue);

        merge(target, "user", user);
    }

    public IssueDto getIssue() {
        return issue;
    }

    public void setIssue(IssueDto issue) {
        if (issue == null)
            throw new NullPointerException("issue");
        this.issue = issue;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        if (user == null)
            throw new NullPointerException("user");
        this.user = user;
    }

}
