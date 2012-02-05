package com.bee32.sem.mail.dto;

import java.util.List;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.mail.entity.MailFolder;

public class MailFolderDto
        extends UIEntityDto<MailFolder, Integer> {

    private static final long serialVersionUID = 1L;

    public static final int MAILS = 0x80000000;

    byte priority;
    int order;
    String name;
    int color;
    List<MailDeliveryDto> mails;

    public MailFolderDto() {
        super();
    }

    public MailFolderDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(MailFolder source) {
        priority = source.getPriority();
        order = source.getOrder();
        name = source.getName();
        color = source.getColor();

        if (selection.contains(MAILS))
            mails = marshalList(MailDeliveryDto.class, selection.bits & ~MAILS, source.getMails());
    }

    @Override
    protected void _unmarshalTo(MailFolder target) {
        target.setPriority(priority);
        target.setOrder(order);
        target.setLabel(name);
        target.setColor(color);

        if (selection.contains(MAILS))
            mergeList(target, "mails", mails);
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {

        priority = map.getByte("priority");
        order = map.getInt("order");
        name = map.getString("name");
        color = map.getInt("color");

        // skip ITEMS.
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public List<MailDeliveryDto> getMails() {
        return mails;
    }

    public void setMails(List<MailDeliveryDto> mails) {
        this.mails = mails;
    }

}
