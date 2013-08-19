package com.bee32.sem.mail;

import javax.persistence.Transient;

import com.bee32.plover.arch.util.Flags32;

/**
 * 邮件标志
 *
 * <p lang="en">
 * Mail Flags
 */
public class MailFlags
        extends Flags32 {

    private static final long serialVersionUID = 1L;

    public static final int REPLIED = 1 << 0;
    public static final int REPLY_MAIL = 1 << 1;
    public static final int FORWARDED = 1 << 1;
    public static final int FORWARD_MAIL = 1 << 2;
    public static final int RECEIPT_ASK = 1 << 10;
    public static final int RECEIPT_ACK = 1 << 11;
    public static final int READ_MARK = 1 << 20;
    public static final int SPAM_MARK = 1 << 21;
    public static final int DELETED_MARK = 1 << 22;

    @Transient
    public boolean isReplied() {
        return this.contains(REPLIED);
    }

    public void setReplied(boolean val) {
        this.set(REPLIED, val);
    }

    @Transient
    public boolean isForwarded() {
        return this.contains(FORWARDED);
    }

    public void setForwarded(boolean val) {
        this.set(FORWARDED, val);
    }

    @Transient
    public boolean isReplyMail() {
        return this.contains(REPLY_MAIL);
    }

    public void setReplyMail(boolean val) {
        this.set(REPLY_MAIL, val);
    }

    @Transient
    public boolean isForwardMail() {
        return this.contains(FORWARD_MAIL);
    }

    public void setForwardMail(boolean val) {
        this.set(FORWARD_MAIL, val);
    }

    @Transient
    public boolean isReceiptAsked() {
        return this.contains(RECEIPT_ASK);
    }

    public void setReceiptAsked(boolean val) {
        this.set(RECEIPT_ASK, val);
    }

    @Transient
    public boolean isReceiptAcknowledged() {
        return this.contains(RECEIPT_ASK);
    }

    public void setReceiptAcknowledged(boolean val) {
        this.set(RECEIPT_ACK, val);
    }

    @Transient
    public boolean isMarkAsRead() {
        return this.contains(READ_MARK);
    }

    public void setMarkAsRead(boolean val) {
        this.set(READ_MARK, val);
    }

    @Transient
    public boolean isMarkAsSpam() {
        return this.contains(SPAM_MARK);
    }

    public void setMarkAsSpam(boolean val) {
        this.set(SPAM_MARK, val);
    }

    @Transient
    public boolean isMarkAsDeleted() {
        return this.contains(DELETED_MARK);
    }

    public void setMarkAsDeleted(boolean val) {
        this.set(DELETED_MARK, val);
    }

}
