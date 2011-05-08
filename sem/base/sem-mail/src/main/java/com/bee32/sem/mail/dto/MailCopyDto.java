package com.bee32.sem.mail.dto;

import java.util.Date;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
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
        mail = new MailDto(source.getMail());
        owner = new UserDto(0, source.getOwner());
        ownerType = source.getOwnerType();

        mailBox = new MailBoxDto(0, source.getMailBox());
        flags.bits = source.getFlags().bits;

        sentDate = source.getSentDate();
        sendError = source.getSendError();

        receivedDate = source.getReceiptDate();
        receiptDate = source.getReceiptDate();
    }

    @Override
    protected void _unmarshalTo(MailCopy target) {
        merge(target, "mail", mail);
        merge(target, "owner", owner);
        target.setOwnerType(ownerType);

        merge(target, "mailBox", mailBox);
        target.setFlagBits(flags.bits);

        target.setSentDate(sentDate);
        target.setSendError(sendError);
        target.setReceivedDate(receivedDate);
        target.setReceiptDate(receiptDate);
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {

        ownerType = MailOwnerType.valueOf(map.getString("ownerType"));

        flags.bits = map.getInt("flags");
    }

    public MailDto getMail() {
        return mail;
    }

    public void setMail(MailDto mail) {
        this.mail = mail;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public MailOwnerType getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(MailOwnerType ownerType) {
        this.ownerType = ownerType;
    }

    public MailBoxDto getMailBox() {
        return mailBox;
    }

    public void setMailBox(MailBoxDto mailBox) {
        this.mailBox = mailBox;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public String getSendError() {
        return sendError;
    }

    public void setSendError(String sendError) {
        this.sendError = sendError;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public MailFlags getFlags() {
        return flags;
    }

}
