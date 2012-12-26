package com.bee32.sem.track.dto;

import javax.free.ParseException;

import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.track.entity.IssueFav;

public class IssueFavDto
        extends UIEntityDto<IssueFav, Long> {

    private static final long serialVersionUID = 1L;

    IssueDto issue;
    UserDto user;

    @Override
    protected void _marshal(IssueFav source) {
        issue = marshal(IssueDto.class, source.getIssue());
        user = marshal(UserDto.class, source.getUser());
    }

    @Override
    protected void _unmarshalTo(IssueFav target) {
        merge(target, "issue", issue);
        merge(target, "user", user);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public IssueDto getIssue() {
        return issue;
    }

    public void setIssue(IssueDto issue) {
        this.issue = issue;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

}
