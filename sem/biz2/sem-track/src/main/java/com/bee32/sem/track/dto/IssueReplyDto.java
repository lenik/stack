package com.bee32.sem.track.dto;

import javax.free.ParseException;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.MomentIntervalDto;
import com.bee32.sem.track.entity.IssueReply;

public class IssueReplyDto
        extends MomentIntervalDto<IssueReply>
        implements IEnclosedObject<IssueDto> {

    private static final long serialVersionUID = 1L;

    UserDto replier;
    IssueDto issue;
    String text;
    boolean flag = false;

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
        UserDto current = SessionUser.getInstance().getUser();
        replier = marshal(UserDto.class, source.getReplier());
        issue = mref(IssueDto.class, source.getIssue());
        text = source.getText();
        if (current.getId() == replier.getId())
            flag = true;
    }

    @Override
    protected void _unmarshalTo(IssueReply target) {
        merge(target, "replier", replier);
        merge(target, "issue", issue);
        target.setText(text);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public UserDto getReplier() {
        return replier;
    }

    public void setReplier(UserDto replier) {
        this.replier = replier;
    }

    public IssueDto getIssue() {
        return issue;
    }

    public void setIssue(IssueDto issue) {
        if (issue == null)
            throw new NullPointerException("issue");
        this.issue = issue;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
