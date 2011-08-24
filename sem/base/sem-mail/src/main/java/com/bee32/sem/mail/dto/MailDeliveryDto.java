package com.bee32.sem.mail.dto;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.mail.MailFlags;
import com.bee32.sem.mail.entity.MailDelivery;
import com.bee32.sem.mail.entity.MailOrientation;

public class MailDeliveryDto
        extends TxEntityDto<MailDelivery> {

    private static final long serialVersionUID = 1L;

    MailDto mail;
    MailOrientation orientation;

    MailFolderDto folder;

    String sendError;

    final MailFlags flags = new MailFlags();

    @Override
    protected void _marshal(MailDelivery source) {
        mail = mref(MailDto.class, source.getMail());
        folder = mref(MailFolderDto.class, source.getFolder());
        orientation = source.getOrientation();

        flags.bits = source.getFlags();

        sendError = source.getSendError();
    }

    @Override
    protected void _unmarshalTo(MailDelivery target) {
        merge(target, "mail", mail);
        target.setParty(orientation);

        merge(target, "folder", folder);
        target.setFlags(flags.bits);

        target.setSendError(sendError);
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {

        int _orientation = map.getInt("orientation");
        orientation = MailOrientation.valueOf(_orientation);

        flags.bits = map.getInt("flags");
    }

    public MailDto getMail() {
        return mail;
    }

    public void setMail(MailDto mail) {
        this.mail = mail;
    }

    public MailOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(MailOrientation orientation) {
        this.orientation = orientation;
    }

    public MailFolderDto getFolder() {
        return folder;
    }

    public void setFolder(MailFolderDto folder) {
        if (folder == null)
            throw new NullPointerException("folder");
        this.folder = folder;
    }

    public String getSendError() {
        return sendError;
    }

    public void setSendError(String sendError) {
        this.sendError = sendError;
    }

    public MailFlags getFlags() {
        return flags;
    }

}
