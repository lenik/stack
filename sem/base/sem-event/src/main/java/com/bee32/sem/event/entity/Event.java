package com.bee32.sem.event.entity;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.IEntity;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.sem.event.EventFlags;
import com.bee32.sem.event.EventState;

@Entity
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("EVT")
public class Event
        extends EntityBean<Long>
        implements IEvent, ITypeAbbrAware {

    private static final long serialVersionUID = 1L;

    private EventCategory category;
    private Class<?> source;

    private int priority;

    private final EventFlags flags = new EventFlags();
    private boolean closed;
    private int state;
    private EventStatus status; // status -> flags, closed, state.

    private User actor;

    private String subject;
    private String message;
    private Date beginTime = new Date();
    private Date endTime;

    private Class<?> refClass;
    private long refId;
    private String refAlt;

    private Set<Principal> observers;

    public Event() {
        super();
    }

    public Event(String name) {
        super(name);
    }

    @ManyToOne
    @Index(name = "category")
    @Override
    public EventCategory getCategory() {
        return category;
    }

    public void setCategory(EventCategory category) {
        this.category = category;
    }

    @Column(length = ABBR_LEN, nullable = false)
    @Index(name = "source")
    String getSource() {
        return ABBR.abbr(source);
    }

    void setSource(String source)
            throws ClassNotFoundException {
        this.source = ABBR.expand(source);
    }

    @Transient
    @Override
    public Class<?> getSourceClass() {
        return source;
    }

    public void setSourceClass(Class<?> sourceClass) {
        this.source = sourceClass;
    }

    @Column(nullable = false)
    @Override
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setPriority(EventPriority priority) {
        this.priority = priority == null ? 0 : priority.getPriority();
    }

    @Column(nullable = false)
    @Override
    public int getFlags() {
        return flags.bits;
    }

    public void setFlags(int bits) {
        flags.bits = bits;
    }

    @Index(name = "closed")
    @Column(nullable = false)
    @Override
    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    @Index(name = "state")
    @Column(nullable = false)
    @Override
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setState(EventState state) {
        this.state = state == null ? 0 : state.getId();
    }

    @ManyToOne
    @Override
    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    @Index(name = "actor")
    @ManyToOne(fetch = FetchType.LAZY)
    @Override
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
    @Override
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Index(name = "event_endTime")
    @Temporal(TemporalType.TIMESTAMP)
    @Override
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(length = ABBR_LEN)
    String getRefType() {
        return ABBR.abbr(refClass);
    }

    void setRefType(String refType)
            throws ClassNotFoundException {
        this.refClass = ABBR.expand(refType);
    }

    @Transient
    @Override
    public Class<?> getRefClass() {
        return refClass;
    }

    public void setRefClass(Class<?> refClass) {
        this.refClass = refClass;
    }

    public void setRef(IEntity<?> refEntity) {
        if (refEntity == null) {
            setRefClass((Class<?>) null);
            refId = 0L;
            refAlt = null;
        } else {
            setRefClass(refEntity.getClass());
            Object id = refEntity.getId();
            if (id instanceof Number) {
                refId = ((Number) id).longValue();
                refAlt = null;
            } else {
                refId = 0L;
                refAlt = String.valueOf(id);
            }
        }
    }

    @Override
    public long getRefId() {
        return refId;
    }

    public void setRefId(long refId) {
        this.refId = refId;
    }

    @Column(length = 30)
    @Override
    public String getRefAlt() {
        return refAlt;
    }

    public void setRefAlt(String refAlt) {
        this.refAlt = refAlt;
    }

    @ManyToMany
    @JoinTable(name = "EventObserver")
    @Override
    public Set<Principal> getObservers() {
        if (observers == null) {
            synchronized (this) {
                if (observers == null) {
                    observers = new HashSet<Principal>();
                }
            }
        }
        return observers;
    }

    public void setObservers(Set<Principal> observers) {
        this.observers = observers;
    }

    public void setObservers(List<? extends Principal> observers) {
        this.observers = new HashSet<Principal>(observers);
    }

}
