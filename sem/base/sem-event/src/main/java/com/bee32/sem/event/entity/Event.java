package com.bee32.sem.event.entity;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.free.IllegalUsageException;
import javax.persistence.*;

import org.hibernate.annotations.Index;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.IEntity;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.plover.ox1.color.Pink;
import com.bee32.sem.event.EventFlags;
import com.bee32.sem.event.EventState;

@Entity
@Pink
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("EVT")
@SequenceGenerator(name = "idgen", sequenceName = "event_seq", allocationSize = 1)
public class Event
        extends MomentInterval
        implements IEvent, ITypeAbbrAware {

    private static final long serialVersionUID = 1L;

    private char type = EventType.EVENT.getValue();
    private EventCategory category;
    private Class<?> sourceClass;

    private int priority;

    private final EventFlags flags = new EventFlags();
    private boolean closed;
    private int _state;
    private EventStatus status; // status -> flags, closed, state.

    private User actor;

    private String subject;
    private String message;

    /** for Task only */
    private Date scheduledEndTime;

    private Class<?> refType;
    private long refId;
    private String refAlt;

    private String seeAlsos;

    private Set<Principal> observers = new HashSet<Principal>();

    @Deprecated
    public Event() {
    }

    public Event(Object source, EventType type) {
        this(source.getClass(), type);
    }

    public Event(Class<?> sourceClass, EventType type) {
        this.setSourceClass(sourceClass);
        this.setType(type);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof Event)
            _populate((Event) source);
        else
            super.populate(source);
    }

    protected void _populate(Event o) {
        super._populate(o);
        type = o.type;
        category = o.category;
        sourceClass = o.sourceClass;
        priority = o.priority;
        flags.bits = o.flags.bits;
        closed = o.closed;
        _state = o._state;
        status = o.status;
        actor = o.actor;
        subject = o.subject;
        message = o.message;
        scheduledEndTime = o.scheduledEndTime;
        refType = o.refType;
        refId = o.refId;
        refAlt = o.refAlt;
        seeAlsos = o.seeAlsos;
        observers = new HashSet<Principal>(o.observers);
    }

    @Transient
    public EventType getType() {
        return EventType.forValue(type);
    }

    public void setType(EventType type) {
        if (type == null)
            throw new NullPointerException("type");
        this.type = type.getValue();
    }

    @Column(name = "type", nullable = false)
    char get_type() {
        return type;
    }

    public void set_type(char type) {
        this.type = type;
    }

    @ManyToOne
    @Index(name = "##_cat")
    @Override
    public EventCategory getCategory() {
        return category;
    }

    public void setCategory(EventCategory category) {
        this.category = category;
    }

    @Column(name = "source", length = ABBR_LEN, nullable = false)
    @Index(name = "##_source")
    String getSourceClassId() {
        String abbr = ABBR.abbr(sourceClass);
        if (abbr == null)
            throw new IllegalUsageException("Can't abbrev class: " + sourceClass);
        return abbr;
    }

    void setSourceClassId(String sourceClassId) {
        if (sourceClassId == null)
            throw new NullPointerException("sourceClassId");
        try {
            Class<?> theClass = ABBR.expand(sourceClassId);
            if (theClass == null)
                throw new IllegalArgumentException("Bad class id: " + sourceClassId);
            sourceClass = theClass;
        } catch (ClassNotFoundException e) {
            throw new IllegalUsageException(e);
        }
    }

    @Transient
    @Override
    public Class<?> getSourceClass() {
        return sourceClass;
    }

    public void setSourceClass(Class<?> sourceClass) {
        if (sourceClass == null)
            throw new NullPointerException("sourceClass");
        this.sourceClass = sourceClass;
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

    @Index(name = "##_closed")
    @Column(nullable = false)
    @Override
    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    @Index(name = "##_state")
    @Column(nullable = false)
    @Override
    public int getState() {
        return _state;
    }

    public void setState(int stateVal) {
        this._state = stateVal;
    }

    public void setState(EventState<?> state) {
        this._state = state == null ? 0 : state.getValue();
    }

    @ManyToOne
    @Override
    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    @Index(name = "##_actor")
    @ManyToOne(fetch = FetchType.LAZY)
    @Override
    public User getActor() {
        return actor;
    }

    public void setActor(User actor) {
        this.actor = actor;
    }

    @Index(name = "##_subject")
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

    @Index(name = "##_scheduledEndTime")
    @Temporal(TemporalType.TIMESTAMP)
    @Override
    public Date getScheduledEndTime() {
        return scheduledEndTime;
    }

    public void setScheduledEndTime(Date scheduledEndTime) {
        this.scheduledEndTime = scheduledEndTime;
    }

    @Column(name = "refType", length = ABBR_LEN)
    String getRefTypeId() {
        return ABBR.abbr(refType);
    }

    void setRefTypeId(String refClassId)
            throws ClassNotFoundException {
        this.refType = ABBR.expand(refClassId);
    }

    @Transient
    @Override
    public Class<?> getRefType() {
        return refType;
    }

    public void setRefType(Class<?> refClass) {
        this.refType = refClass;
    }

    public void setRef(IEntity<?> refEntity) {
        if (refEntity == null) {
            setRefType((Class<?>) null);
            refId = 0L;
            refAlt = null;
        } else {
            setRefType(refEntity.getClass());
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

    @Column(length = 1000)
    public String getSeeAlsos() {
        return seeAlsos;
    }

    public void setSeeAlsos(String seeAlsos) {
        this.seeAlsos = seeAlsos;
    }

    @ManyToMany
    @JoinTable(name = "EventObserver",
    /*            */joinColumns = @JoinColumn(name = "event"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "observer"))
    @Override
    public Set<Principal> getObservers() {
        return observers;
    }

    public void setObservers(Set<Principal> observers) {
        if (observers == null)
            throw new NullPointerException("observers");
        this.observers = observers;
    }

    public int addObservers(Principal... observers) {
        int n = 0;
        for (Principal observer : observers) {
            if (addObserver(observer))
                n++;
        }
        return n;
    }

    public boolean addObserver(Principal observer) {
        if (observer == null)
            throw new NullPointerException("observer");
        return observers.add(observer);
    }

    public boolean removeObserver(Principal observer) {
        return observers.remove(observer);
    }

}
