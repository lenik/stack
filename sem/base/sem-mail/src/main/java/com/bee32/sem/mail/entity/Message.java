package com.bee32.sem.mail.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 消息
 */
@MappedSuperclass
public abstract class Message<self_t extends Message<?>>
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int SUBJECT_LENGTH = DESCRIPTION_LENGTH;
    public static final int TEXT_LENGTH = 100000;

    private int priority = 0;

    // private Message template;
    private String text = "";
    private self_t prev;
    private List<self_t> follows;

    /**
     * 优先级
     *
     * 消息的优先级。
     */
    @Column(name = "priority", nullable = false)
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * 标题
     *
     * 消息的标题。
     */
    @Transient
    public String getSubject() {
        return getDescription();
    }

    public void setSubject(String subject) {
        setDescription(subject);
    }

    /**
     * 正文
     *
     * 消息的主体文字。
     */
    @Column(length = TEXT_LENGTH, nullable = false)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * 前文
     *
     * 说明消息回复的前文。
     */
    @ManyToOne
    public self_t getPrev() {
        return prev;
    }

    public void setPrev(self_t prev) {
        this.prev = prev;
    }

    /**
     * 后文
     *
     * 说明有哪些消息回复了本消息。
     */
    @OneToMany(mappedBy = "prev", fetch = FetchType.LAZY)
    public List<self_t> getFollows() {
        return follows;
    }

    public void setFollows(List<self_t> follows) {
        if (follows == null)
            throw new NullPointerException("follows");
        this.follows = follows;
    }

}
