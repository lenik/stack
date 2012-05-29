package com.bee32.sem.mail.dto;

import java.util.List;

import javax.free.ParseException;
import javax.free.TypeConvertException;
import javax.validation.constraints.NotNull;

import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.c.CEntityDto;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.mail.MailPriority;
import com.bee32.sem.mail.MailType;
import com.bee32.sem.mail.entity.Mail;

public class MailDto
        extends CEntityDto<Mail, Long> {

    private static final long serialVersionUID = 1L;

    public static final int REFERRER_MASK = 0x0000000f;
    public static final int COPIES = 1 << 16;

    MailType type;
    MailPriority priority;

    String from;
    String recipient;
    String replyTo;

    UserDto fromUser;
    List<UserDto> recipientUsers;
    UserDto replyToUser;

    String cc;
    String bcc;

    String subject;
    String body;

    MailDto referrer;

    String recipients;

    public MailDto() {
        super();
    }

    public MailDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(Mail source) {
        type = source.getType();
        priority = source.getPriority();

        from = source.getFrom();
        recipient = source.getRecipient();
        replyTo = source.getReplyTo();

        fromUser = mref(UserDto.class, source.getFromUser());
        recipientUsers = mrefList(UserDto.class, 0, source.getRecipientUsers());
        replyToUser = mref(UserDto.class, source.getReplyToUser());

        cc = source.getCc();
        bcc = source.getBcc();

        subject = source.getSubject();
        body = source.getBody();

        int otherbits = selection.bits & ~REFERRER_MASK;
        int refs = selection.bits & REFERRER_MASK;
        if (refs > 0)
            referrer = mref(MailDto.class, otherbits | --refs, source.getReferrer());

        recipients = assemblerRecipients();

    }

    @Override
    protected void _unmarshalTo(Mail target) {
        target.setType(type);
        // target.setMailbox(mailbox);
        target.setPriority(priority);

        target.setFrom(from);
        target.setRecipient(recipient);
        target.setReplyTo(replyTo);

        merge(target, "fromUser", fromUser);
        mergeList(target, "recipientUsers", recipientUsers);
        merge(target, "replyToUser", replyToUser);

        target.setCc(cc);
        target.setBcc(bcc);

        target.setSubject(subject);
        target.setBody(body);

        int refs = selection.bits & REFERRER_MASK;
        if (refs > 0)
            target.setReferrer(referrer.unmarshal());

    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {

        int _type = map.getInt("type");
        type = MailType.forValue(_type);

        // String _mailbox = map.getString("mailbox");

        int _priority = map.getInt("priority");
        priority = MailPriority.forValue(_priority);

        from = map.getString("from");
        recipient = map.getString("to");
        replyTo = map.getString("replyTo");

        cc = map.getString("cc");
        bcc = map.getString("bcc");

        subject = map.getString("subject");
        body = map.getString("body");

        // dates...
    }

    @NotNull
    public MailType getType() {
        return type;
    }

    public void setType(MailType type) {
        if (type == null)
            throw new NullPointerException("type");
        this.type = type;
    }

    @NotNull
    public MailPriority getPriority() {
        return priority;
    }

    public void setPriority(MailPriority priority) {
        if (priority == null)
            throw new NullPointerException("priority");
        this.priority = priority;
    }

    @NLength(min = 1, max = Mail.FROM_LENGTH)
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = TextUtil.normalizeSpace(from);
    }

    @NLength(min = 1, max = Mail.RECIPIENT_LENGTH)
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = TextUtil.normalizeSpace(recipient);
    }

    @NLength(max = Mail.REPLY_TO_LENGTH)
    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = TextUtil.normalizeSpace(replyTo);
    }

    public UserDto getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserDto fromUser) {
        this.fromUser = fromUser;
    }

    public List<UserDto> getRecipientUsers() {
        return recipientUsers;
    }

    public void setRecipientUser(List<UserDto> recipientUsers) {
        if (recipientUsers == null)
            throw new NullPointerException("recipientUsers");
        this.recipientUsers = recipientUsers;
    }

    public boolean addRecipientUser(UserDto recipientUser) {
        if (recipientUser == null)
            throw new NullPointerException("recipientUser");
        if (!recipientUsers.contains(recipientUser)) {
            recipientUsers.add(recipientUser);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeRecipientUser(UserDto recipientUser) {
        if (recipientUser == null)
            throw new NullPointerException("recipientUser");
        return recipientUsers.remove(recipientUser);
    }

    public UserDto getReplyToUser() {
        return replyToUser;
    }

    public void setReplyToUser(UserDto replyToUser) {
        this.replyToUser = replyToUser;
    }

    @NLength(max = Mail.CC_LENGTH)
    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = TextUtil.normalizeSpace(cc);
    }

    @NLength(max = Mail.BCC_LENGTH)
    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = TextUtil.normalizeSpace(bcc);
    }

    @NLength(min = 1, max = Mail.SUBJECT_LENGTH)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = TextUtil.normalizeSpace(subject);
    }

    @NLength(min = 1, max = Mail.BODY_LENGTH)
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = TextUtil.normalizeSpace(body);
    }

    public MailDto getReferrer() {
        return referrer;
    }

    public void setReferrer(MailDto referrer) {
        this.referrer = referrer;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = TextUtil.normalizeSpace(recipients);
    }

    public String assemblerRecipients() {
        String temp = null;
        if (recipientUsers.size() > 0) {
            for (UserDto recipient : recipientUsers) {
                if (temp == null)
                    temp = recipient.getDisplayName();
                else
                    temp += "," + recipient.getDisplayName();
            }
        } else
            temp = recipient == null ? "" : recipient;
        return temp;
    }

}
