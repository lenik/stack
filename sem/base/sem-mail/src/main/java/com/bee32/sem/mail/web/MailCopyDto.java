package com.bee32.sem.mail.web;

import java.util.Date;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.mail.MailFlags;
import com.bee32.sem.mail.MailOwnerType;
import com.bee32.sem.mail.entity.MailCopy;

public class MailCopyDto
        extends EntityDto<MailCopy, Long> {

    private static final long serialVersionUID = 1L;

    MailDto mail;
    UserDto owner;
    MailOwnerType ownerType;

    MailBoxDto mailBox;

    final MailFlags flags = new MailFlags();

    Date sentDate;
    String sendError;

    Date receivedDate;
    Date receiptDate;

    @Override
    protected void _marshal(MailCopy source) {
        mail = new MailDto().marshal(source.getMail());
        owner = new UserDto().marshal(source.getOwner());
        ownerType = source.getOwnerType();

        mailBox = new MailBoxDto(0).marshal(source.getMailBox());
        flags.bits = source.getFlags().bits;

        sentDate = source.getSentDate();
        sendError = source.getSendError();

        receivedDate = source.getReceiptDate();
        receiptDate = source.getReceiptDate();
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, MailCopy target) {
        target.setMail(unmarshal(mail));
        target.setOwner(unmarshal(owner));
        target.setOwnerType(ownerType);

        target.setMailBox(unmarshal(mailBox));
        target.setFlagBits(flags.bits);

        target.setSentDate(sentDate);
        target.setSendError(sendError);
        target.setReceivedDate(receivedDate);
        target.setReceiptDate(receiptDate);
    }

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
        super.parse(map);

        ownerType = MailOwnerType.valueOf(map.getString("ownerType"));

        flags.bits = map.getInt("flags");
    }

}
