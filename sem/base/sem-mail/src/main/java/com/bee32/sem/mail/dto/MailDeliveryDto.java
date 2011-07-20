package com.bee32.sem.mail.dto;

import java.util.Date;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.mail.MailFlags;
import com.bee32.sem.mail.entity.MailDelivery;
import com.bee32.sem.mail.entity.MailParty;

public class MailDeliveryDto
        extends EntityDto<MailDelivery, Long> {

    private static final long serialVersionUID = 1L;

    MailDto mail;
    MailParty party;

    MailFolderDto folder;

    Date sentDate;
    String sendError;

    Date receivedDate;
    Date receiptDate;

    final MailFlags flags = new MailFlags();

    @Override
    protected void _marshal(MailDelivery source) {
        mail = mref(MailDto.class, source.getMail());
        folder = mref(MailFolderDto.class, source.getFolder());
        party = source.getParty();

        flags.bits = source.getFlags();

        sentDate = source.getSentDate();
        sendError = source.getSendError();

        receivedDate = source.getReceiptDate();
        receiptDate = source.getReceiptDate();
    }

    @Override
    protected void _unmarshalTo(MailDelivery target) {
        merge(target, "mail", mail);
        target.setParty(party);

        merge(target, "folder", folder);
        target.setFlags(flags.bits);

        target.setSentDate(sentDate);
        target.setSendError(sendError);
        target.setReceivedDate(receivedDate);
        target.setReceiptDate(receiptDate);
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {

        party = MailParty.valueOf(map.getString("party"));

        flags.bits = map.getInt("flags");
    }

    public MailDto getMail() {
        return mail;
    }

    public void setMail(MailDto mail) {
        this.mail = mail;
    }

    public MailParty getParty() {
        return party;
    }

    public void setParty(MailParty party) {
        this.party = party;
    }

    public MailFolderDto getFolder() {
        return folder;
    }

    public void setFolder(MailFolderDto folder) {
        this.folder = folder;
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
