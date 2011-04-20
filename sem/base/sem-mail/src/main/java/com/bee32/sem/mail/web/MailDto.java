package com.bee32.sem.mail.web;

import java.util.Date;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.mail.MailType;
import com.bee32.sem.mail.entity.Mail;

public class MailDto
        extends EntityDto<Mail, Long> {

    private static final long serialVersionUID = 1L;

    MailType type;

    String from;
    String to;
    String replyTo;

    User fromUser;
    User toUser;
    User replyToUser;

    String cc;
    String bcc;

    String subject;
    String body;

    Date sentDate;
    Date receivedDate;
    Date receiptDate;

    @Override
    protected void _marshal(Mail source) {
        from = source.getFrom();
        to = source.getTo();
        replyTo = source.getReplyTo();

        cc = source.getCc();
        bcc = source.getBcc();

        subject = source.getSubject();
        body = source.getBody();
    }

    @Override
    protected void _unmarshalTo(Mail target) {
        target.setFrom(from);
        target.setTo(to);
        target.setReplyTo(replyTo);
        target.setCc(cc);
        target.setBcc(bcc);
        target.setSubject(subject);
        target.setBody(body);
    }

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
        super.parse(map);
    }

}
