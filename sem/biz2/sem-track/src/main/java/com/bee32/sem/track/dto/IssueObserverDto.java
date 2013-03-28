package com.bee32.sem.track.dto;

import java.io.Serializable;

import javax.free.ParseException;

import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.arch.util.dto.BaseDto;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.track.entity.IssueObserver;

public class IssueObserverDto
        extends UIEntityDto<IssueObserver, Long> {

    IssueDto issue;
    UserDto observer;
    int rank;
    boolean manager;
    boolean fav;
    String stateText;

    private static final long serialVersionUID = 1L;

    @Override
    protected void _marshal(IssueObserver source) {
        issue = mref(IssueDto.class, source.getIssue());
        observer = mref(UserDto.class, source.getObserver());
        rank = source.getRank();
        manager = source.isManager();
        fav = source.isFav();
        stateText = source.getStateText();
    }

    @Override
    protected void _unmarshalTo(IssueObserver target) {
        merge(target, "issue", issue);
        merge(target, "observer", observer);
        target.setRank(rank);
        target.setManager(manager);
        target.setFav(fav);
        target.setStateText(stateText);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
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
    public UserDto getObserver() {
        return observer;
    }

    public void setObserver(UserDto observer) {
        if (observer == null)
            throw new NullPointerException("observer");
        this.observer = observer;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    @NLength(max = IssueObserver.STATE_TEXT_LENGTH)
    public String getStateText() {
        return stateText;
    }

    public void setStateText(String stateText) {
        if (stateText == null)
            throw new NullPointerException("stateText");
        this.stateText = stateText;
    }

    @Override
    protected Serializable naturalId(BaseDto<?> o) {
        return new IdComposite(//
                naturalIdOpt(issue), //
                naturalIdOpt(observer));
    }

    boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
