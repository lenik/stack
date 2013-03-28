package com.bee32.sem.track.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.icsf.principal.GroupDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.track.entity.IssueCcGroup;

public class IssueCcGroupDto
        extends UIEntityDto<IssueCcGroup, Long> {

    private static final long serialVersionUID = 1L;

    IssueDto issue;
    GroupDto group;

    @Override
    protected void _marshal(IssueCcGroup source) {
        issue = mref(IssueDto.class, source.getIssue());
        group = mref(GroupDto.class, source.getGroup());
    }

    @Override
    protected void _unmarshalTo(IssueCcGroup target) {
        merge(target, "issue", issue);
        merge(target, "group", group);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    // @NotNullId
    public IssueDto getIssue() {
        return issue;
    }

    public void setIssue(IssueDto issue) {
        if (issue == null)
            throw new NullPointerException("issue");
        this.issue = issue;
    }

    // @NotNullId
    public GroupDto getGroup() {
        return group;
    }

    public void setGroup(GroupDto group) {
        if (group == null)
            throw new NullPointerException("group");
        this.group = group;
    }

}
