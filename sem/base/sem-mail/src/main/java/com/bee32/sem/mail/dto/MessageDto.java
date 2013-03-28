package com.bee32.sem.mail.dto;

import java.util.Collections;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.mail.entity.Message;

public class MessageDto<E extends Message<E>, self_t extends MessageDto<E, self_t>>
        extends UIEntityDto<E, Long> {

    private static final long serialVersionUID = 1L;

    public static final int FOLLOWS = 0x00800000;

    int priority;
    String text;
    self_t prev;
    List<self_t> follows;

    @Override
    protected void _copy() {
        follows = CopyUtils.copyList(follows);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void _marshal(E source) {
        priority = source.getPriority();
        text = source.getText();

        prev = (self_t) mref(getClass(), selection.bits, source.getPrev());

        if (selection.contains(FOLLOWS))
            follows = mrefList(getClass(), selection.bits, source.getFollows());
        else
            follows = Collections.emptyList();
    }

    @Override
    protected void _unmarshalTo(E target) {
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

    public self_t getPrev() {
        return prev;
    }

    public void setPrev(self_t prev) {
        this.prev = prev;
    }

    public List<self_t> getFollows() {
        return follows;
    }

    public void setFollows(List<self_t> follows) {
        this.follows = follows;
    }

}
