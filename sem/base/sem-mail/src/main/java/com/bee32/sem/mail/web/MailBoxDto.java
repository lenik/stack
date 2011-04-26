package com.bee32.sem.mail.web;

import java.util.List;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.PropertyAccessor;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.mail.entity.MailBox;
import com.bee32.sem.mail.entity.MailCopy;

public class MailBoxDto
        extends EntityDto<MailBox, Integer> {

    private static final long serialVersionUID = 1L;

    public static final int MAILS = 0x80000000;

    byte priority;
    int order;
    String name;
    String displayName;
    int color;
    List<MailCopyDto> mails;

    public MailBoxDto() {
        super();
    }

    public MailBoxDto(MailBox source) {
        super(source);
    }

    public MailBoxDto(int selection) {
        super(selection);
    }

    public MailBoxDto(MailBox source, int selection) {
        super(source, selection);
    }

    @Override
    protected void _marshal(MailBox source) {
        priority = source.getPriority();
        order = source.getOrder();
        name = source.getName();
        displayName = source.getDisplayName();
        color = source.getColor();

        if (selection.contains(MAILS))
            mails = marshalList(MailCopyDto.class, selection.bits & ~MAILS, source.getMails());
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, MailBox target) {
        target.setPriority(priority);
        target.setOrder(order);
        target.setName(name);
        target.setDisplayName(displayName);
        target.setColor(color);

        if (selection.contains(MAILS))
            with(context, target).unmarshalList(mailsProperty, mails);
    }

    @Override
    public void _parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {

        priority = map.getByte("priority");
        order = map.getInt("order");
        name = map.getString("name");
        displayName = map.getString("displayName");
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public List<MailCopyDto> getMails() {
        return mails;
    }

    public void setMails(List<MailCopyDto> mails) {
        this.mails = mails;
    }

    static final PropertyAccessor<MailBox, List<MailCopy>> mailsProperty = new PropertyAccessor<MailBox, List<MailCopy>>(
            List.class) {

        @Override
        public List<MailCopy> get(MailBox entity) {
            return entity.getMails();
        }

        @Override
        public void set(MailBox entity, List<MailCopy> mails) {
            entity.setMails(mails);
        }

    };

}
