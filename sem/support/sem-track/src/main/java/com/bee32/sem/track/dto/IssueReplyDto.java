package com.bee32.sem.track.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.MomentIntervalDto;
import com.bee32.sem.track.entity.IssueReply;

public class IssueReplyDto
        extends MomentIntervalDto<IssueReply> {

    private static final long serialVersionUID = 1L;

    @Override
    protected void _marshal(IssueReply source) {
    }

    @Override
    protected void _unmarshalTo(IssueReply target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
