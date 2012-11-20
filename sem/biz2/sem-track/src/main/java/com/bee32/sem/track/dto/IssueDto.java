package com.bee32.sem.track.dto;

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

    @Override
    protected void _marshal(Issue source) {
        text = source.getText();
        replay = source.getReplay();
        tags = source.getTags();
        commitish = source.getCommitish();
        if (selection.contains(ATTACHMENTS))
            marshalList(UserFileDto.class, source.getAttachments());
        if (selection.contains(REPLIES))
            marshalList(IssueReplyDto.class, source.getReplies());
    }

    @Override
    protected void _unmarshalTo(Issue target) {
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

}
