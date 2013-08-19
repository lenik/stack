package com.bee32.sem.mail;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;

/**
 * 邮件类型
 *
 * <p lang="en">
 * Mail Type
 */
public class MailType
        extends EnumAlt<Integer, MailType> {

    private static final long serialVersionUID = 1L;

    MailType baseType;

    public MailType(int value, String name) {
        super(value, name);
    }

    public MailType(int value, String name, MailType baseType) {
        super(baseType == null ? value : baseType.value + value, name);
        this.baseType = baseType;
    }

    public static MailType forName(String name) {
        return forName(MailType.class, name);
    }

    public static Collection<MailType> values() {
        return values(MailType.class);
    }

    public static MailType forValue(Integer value) {
        return forValue(MailType.class, value);
    }

    public static MailType forValue(int value) {
        return forValue(new Integer(value));
    }

    public static final MailType __system__ /**/= new MailType(0x00000, "__system__");

    /**
     * 广播消息
     *
     * <p lang="en">
     * Broadcast Message
     */
    public static final MailType BCAST /*     */= new MailType(1, "bcast", __system__);

    /**
     * 系统通知
     *
     * <p lang="en">
     * System Notification
     */
    public static final MailType NOTICE /*    */= new MailType(2, "notice", __system__);

    public static final MailType __issue__ /* */= new MailType(0x10000, "__issue__");

    /**
     * 问题报告
     *
     * <p lang="en">
     * Issue Report
     */
    public static final MailType ISSUE /*     */= new MailType(1, "issue", __issue__);

    public static final MailType __convertion__ = new MailType(0x20000, "__converted__");

    /**
     * 电子邮件交换
     *
     * <p lang="en">
     * Converted E-mail
     */
    public static final MailType EMAIL /*     */= new MailType(1, "email", __convertion__);

    /**
     * 传真交换
     *
     * <p lang="en">
     * Converted Fax
     */
    public static final MailType FAX /*       */= new MailType(2, "fax", __convertion__);

    public static final MailType __social__ /**/= new MailType(0x30000, "__social__");

    /**
     * 用户邮件
     *
     * <p lang="en">
     * User Mail
     */
    public static final MailType USER /*      */= new MailType(1, "user", __social__);

    /**
     * 问题
     *
     * <p lang="en">
     * Question
     */
    public static final MailType QUESTION /*  */= new MailType(2, "question", __social__);

    /**
     * 帖子
     *
     * <p lang="en">
     * User Post
     */
    public static final MailType POST /*      */= new MailType(3, "post", __social__);

    /**
     * 评论
     *
     * <p lang="en">
     * Comment
     */
    public static final MailType COMMENT/*    */= new MailType(4, "comment", __social__);

}
