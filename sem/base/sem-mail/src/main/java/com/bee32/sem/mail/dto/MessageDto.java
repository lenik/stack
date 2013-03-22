package com.bee32.sem.mail.dto;

import java.util.Collections;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.mail.entity.Message;

public class MessageDto
        extends UIEntityDto<Message, Long> {

    private static final long serialVersionUID = 1L;

    public static final int FOLLOWS = 1;

    int priority;
    String text;
    MessageDto prev;
    List<MessageDto> follows;

    @Override
    protected void _copy() {
        follows = CopyUtils.copyList(follows);
    }

    @Override
    protected void _marshal(Message source) {
        priority = source.getPriority();
        text = source.getText();

        prev = mref(MessageDto.class, source.getPrev());

        if (selection.contains(FOLLOWS))
            follows = mrefList(MessageDto.class, source.getFollows());
        else
            follows = Collections.emptyList();
    }

    @Override
    protected void _unmarshalTo(Message target) {
        target.setPriority(priority);
        target.setText(text);

        merge(target, "prev", prev);

        // Auto refreshed by mapped-by.
        // if (selection.contains(FOLLOWS))
        // mergeList(target, "follows", follows);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MessageDto getPrev() {
        return prev;
    }

    public void setPrev(MessageDto prev) {
        this.prev = prev;
    }

    public List<MessageDto> getFollows() {
        return follows;
    }

    public void setFollows(List<MessageDto> follows) {
        this.follows = follows;
    }

}
