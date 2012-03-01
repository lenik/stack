package com.bee32.sem.mail.dto;

import java.util.List;

import javax.free.ParseException;
import javax.free.TypeConvertException;
import javax.free.UnexpectedException;

import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.MomentIntervalDto;
import com.bee32.sem.frame.ui.IEnclosedObject;
import com.bee32.sem.mail.MailFlags;
import com.bee32.sem.mail.entity.MailDelivery;
import com.bee32.sem.mail.entity.MailOrientation;

public class MailDeliveryDto
        extends MomentIntervalDto<MailDelivery>
        implements IEnclosedObject<MailFolderDto> {

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
        target.setOrientation(orientation);

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

    @Override
    public MailFolderDto getEnclosingObject() {
        return getFolder();
    }

    @Override
    public void setEnclosingObject(MailFolderDto enclosingObject) {
        setFolder(enclosingObject);
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

    public String getTee() {
        if (orientation.getLabel().equals(MailOrientation.FROM.getLabel())) {
            UserDto fromUser = mail.getFromUser();
            return fromUser == null ? mail.getFrom() : fromUser.getDisplayName();
        }

        else if (orientation.getLabel().equals(MailOrientation.RECIPIENT.getLabel())) {
            List<UserDto> recipientUsers = mail.getRecipientUsers();
            if (recipientUsers.isEmpty())
                return mail.getRecipient();
            String first = recipientUsers.get(0).getDisplayName();
            if (recipientUsers.size() > 1)
                first += ", ...";
            return first;
        }

        else if (orientation.getLabel().equals(MailOrientation.CC.getLabel()))
            return mail.getCc();

        else if (orientation.getLabel().equals(MailOrientation.BCC.getLabel()))
            return mail.getBcc();

        else
            throw new UnexpectedException();
    }

    public String getRecipients() {
        String temp = null;
        if (mail.isNull())
            temp = "";
        else {
            if (mail.getRecipientUsers().size() > 0) {
                for (UserDto recipient : mail.getRecipientUsers()) {
                    if (temp == null)
                        temp = recipient.getDisplayName();
                    else
                        temp += "," + recipient.getDisplayName();
                }
            } else
                temp = mail.getRecipient();
        }
        return temp;
    }
}
