package com.bee32.sem.track.dto;

import java.io.Serializable;

import javax.free.ParseException;

import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.arch.util.dto.BaseDto;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.track.entity.IssueObserver;

public class IssueObserverDto
        extends EntityDto<IssueObserver, Long> {

    IssueDto issue;
    UserDto user;
    boolean manager = false;
    boolean selected = false;

    private static final long serialVersionUID = 1L;

    @Override
    protected void _marshal(IssueObserver source) {
        issue = marshal(IssueDto.class, 0, source.getIssue());
        user = marshal(UserDto.class, source.getUser());
        manager = source.isManager();
    }

    @Override
    protected void _unmarshalTo(IssueObserver target) {
        merge(target, "issue", issue);
        merge(target, "user", user);
        target.setManager(manager);
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

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    protected Serializable naturalId(BaseDto<?> o) {
        return new IdComposite(naturalIdOpt(user), naturalIdOpt(issue));
    }

}
