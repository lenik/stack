package com.bee32.sem.mail.entity;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.color.UIEntityAuto;

@Entity
@Green
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("-")
@SequenceGenerator(name = "idgen", sequenceName = "message_seq", allocationSize = 1)
public class Message
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int SUBJECT_LENGTH = DESCRIPTION_LENGTH;
    public static final int TEXT_LENGTH = 100000;

    private int priority = 0;

    // private Message template;
    private String text = "";
    private Message prev;
    private List<Message> follows;

    @Column(name = "priority", nullable = false)
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Transient
    public String getSubject() {
        return getDescription();
    }

    public void setSubject(String subject) {
        setDescription(subject);
    }

    @Column(length = TEXT_LENGTH, nullable = false)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @ManyToOne
    public Message getPrev() {
        return prev;
    }

    public void setPrev(Message prev) {
        this.prev = prev;
    }

    @OneToMany(mappedBy = "prev", fetch = FetchType.LAZY)
    public List<Message> getFollows() {
        return follows;
    }

    public void setFollows(List<Message> follows) {
        if (follows == null)
            throw new NullPointerException("follows");
        this.follows = follows;
    }

}
