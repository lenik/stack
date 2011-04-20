package com.bee32.sem.mail.web;

import java.util.Date;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.mail.MailFlags;
import com.bee32.sem.mail.MailType;
import com.bee32.sem.mail.entity.Mail;

public class MailDto
        extends EntityDto<Mail, Long> {

    private static final long serialVersionUID = 1L;

    public static final int REFERRER_MASK = 0x0000000f;

    MailType type;
    MailBoxDto mailBox;

    byte priority;
    // String priorityName;

    final MailFlags flags = new MailFlags();

    String from;
    String to;
    String replyTo;

    UserDto fromUser;
    UserDto toUser;
    UserDto replyToUser;

    String cc;
    String bcc;

    String subject;
    String body;

    MailDto referrer;

    Date sentDate;
    String sendError;

    Date receivedDate;
    Date receiptDate;

    public MailDto() {
        super();
    }

    public MailDto(Mail source) {
        super(source);
    }

    public MailDto(int selection) {
        super(selection);
    }

    public MailDto(Mail source, int selection) {
        super(source, selection);
    }

    @Override
    protected void _marshal(Mail source) {
        type = source.getType();
        mailBox = new MailBoxDto(0).marshal(source.getMailBox());

        priority = source.getPriority();
        flags.bits = source.getFlags().bits;

        from = source.getFrom();
        to = source.getTo();
        replyTo = source.getReplyTo();

        cc = source.getCc();
        bcc = source.getBcc();

        subject = source.getSubject();
        body = source.getBody();

        int otherbits = selection.bits & ~REFERRER_MASK;
        int refs = selection.bits & REFERRER_MASK;
        if (refs > 0)
            referrer = new MailDto(otherbits | --refs).marshal(source.getReferrer());

        sentDate = source.getSentDate();
        sendError = source.getSendError();

        receivedDate = source.getReceiptDate();
        receiptDate = source.getReceiptDate();
    }

    @Override
    protected void _unmarshalTo(Mail target) {
        target.setType(type);
        // target.setMailbox(mailbox);
        target.setPriority(priority);
        target.setFlagBits(flags.bits);

        target.setFrom(from);
        target.setTo(to);
        target.setReplyTo(replyTo);
        target.setCc(cc);
        target.setBcc(bcc);

        target.setSubject(subject);
        target.setBody(body);

        int refs = selection.bits & REFERRER_MASK;
        if (refs > 0)
            target.setReferrer(referrer.unmarshal());

        target.setSentDate(sentDate);
        target.setSendError(sendError);
        target.setReceivedDate(receivedDate);
        target.setReceiptDate(receiptDate);
    }

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
        super.parse(map);

        String _type = map.getString("type");
        type = MailType.valueOf(_type);

        // String _mailbox = map.getString("mailbox");

        priority = map.getByte("priority");
        flags.bits = map.getInt("flags");

        from = map.getString("from");
        to = map.getString("to");
        replyTo = map.getString("replyTo");
        cc = map.getString("cc");
        bcc = map.getString("bcc");

        subject = map.getString("subject");
        body = map.getString("body");

        // dates...
    }

    public MailType getType() {
        return type;
    }

    public void setType(MailType type) {
        this.type = type;
    }

    public MailBoxDto getMailBox() {
        return mailBox;
    }

    public void setMailBox(MailBoxDto mailbox) {
        this.mailBox = mailbox;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public MailFlags getFlags() {
        return flags;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public UserDto getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserDto fromUser) {
        this.fromUser = fromUser;
    }

    public UserDto getToUser() {
        return toUser;
    }

    public void setToUser(UserDto toUser) {
        this.toUser = toUser;
    }

    public UserDto getReplyToUser() {
        return replyToUser;
    }

    public void setReplyToUser(UserDto replyToUser) {
        this.replyToUser = replyToUser;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public MailDto getReferrer() {
        return referrer;
    }

    public void setReferrer(MailDto referrer) {
        this.referrer = referrer;
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

}
