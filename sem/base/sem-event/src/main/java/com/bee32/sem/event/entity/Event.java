package com.bee32.sem.event.entity;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.util.AliasUtil;
import com.bee32.plover.orm.util.TypeAbbr;

@Entity
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("")
public class Event
        extends EntityBean<Long>
        implements IEvent {

    private static final long serialVersionUID = 1L;

    private String category;
    private int priority;
    private String state;

    private User actor;

    private String subject;
    private String message;
    private Date beginTime;
    private Date endTime;

    private String refType;
    private transient Class<?> refClass;
    private long refId;
    private String refAlt;

    public Event() {
        super();
    }

    public Event(String name) {
        super(name);
    }

    @Index(name = "category")
    @Column(length = TypeAbbr.DEFAULT_SIZE, nullable = false)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Column(nullable = false)
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Index(name = "state")
    @Column(length = 10)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Index(name = "actor")
    @ManyToOne(fetch = FetchType.LAZY)
    public User getActor() {
        return actor;
    }

    public void setActor(User actor) {
        this.actor = actor;
    }

    @Index(name = "subject")
    @Column(length = 100)
    @Override
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column(length = 1000)
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Index(name = "event_beginTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Index(name = "event_endTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(length = TypeAbbr.DEFAULT_SIZE)
    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    @Column(length = 30)
    @Transient
    @Override
    public Class<?> getRefClass() {
        if (refClass == null) {
            if (refType == null)
                return null;
            else {
                refClass = AliasUtil.getAliasedType(refType);
                if (refClass == null)
                    try {
                        // Entity-Alias? ...
                        refClass = Class.forName(refType);
                    } catch (ClassNotFoundException e) {
                        refClass = null;
                    }
            }
        }
        return refClass;
    }

    public void setRefClass(Class<?> refClass) {
        this.refClass = refClass;
        this.refType = refClass == null ? null : refClass.getName();
    }

    public long getRefId() {
        return refId;
    }

    public void setRefId(long refId) {
        this.refId = refId;
    }

    @Column(length = 30)
    public String getRefAlt() {
        return refAlt;
    }

    public void setRefAlt(String refAlt) {
        this.refAlt = refAlt;
    }

}
