package com.bee32.sem.track.dto;

import java.util.Collections;
import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.MomentIntervalDto;
import com.bee32.sem.file.dto.UserFileDto;
import com.bee32.sem.track.entity.Issue;
import com.bee32.sem.track.entity.IssueState;

public class IssueDto
        extends MomentIntervalDto<Issue> {

    private static final long serialVersionUID = 1L;
    public static final int ATTACHMENTS = 1;
    public static final int REPLIES = 2;

    IssueState state = IssueState.NEW;

    String text = "";
    String replay = "";

    String tags = "";
    String commitish;

    List<UserFileDto> attachments;
    List<IssueReplyDto> replies;

    boolean contentEditable = true;

    @Override
    protected void _marshal(Issue source) {
        state = source.getState();
        text = source.getText();
        replay = source.getReplay();
        tags = source.getTags();
        commitish = source.getCommitish();
        if (selection.contains(ATTACHMENTS))
            attachments = marshalList(UserFileDto.class, source.getAttachments());
        else
            attachments = Collections.emptyList();

        if (selection.contains(REPLIES))
            replies = marshalList(IssueReplyDto.class, source.getReplies());
        else
            replies = Collections.emptyList();
    }

    @Override
    protected void _unmarshalTo(Issue target) {
        target.setState(state);
        target.setText(text);
        target.setReplay(replay);
        target.setTags(tags);
        target.setCommitish(commitish);
        mergeList(target, "attachments", attachments);
        mergeList(target, "replies", replies);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public void addReply(IssueReplyDto reply) {
        replies.add(reply);
    }

    public Character getStateValue() {
        return state.getValue();
    }

    public void setStateValue(Character stateValue) {
        state = IssueState.forValue(stateValue);
    }

    public String getStateName() {
        return state.getName();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReplay() {
        return replay;
    }

    public void setReplay(String replay) {
        this.replay = replay;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCommitish() {
        return commitish;
    }

    public void setCommitish(String commitish) {
        this.commitish = commitish;
    }

    public List<UserFileDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<UserFileDto> attachments) {
        this.attachments = attachments;
    }

    public List<IssueReplyDto> getReplies() {
        return replies;
    }

    public void setReplies(List<IssueReplyDto> replies) {
        this.replies = replies;
    }

    public boolean isContentEditable() {
        return contentEditable;
    }

    public void setContentEditable(boolean contentEditable) {
        this.contentEditable = contentEditable;
    }

}
