package com.bee32.sem.event.web;

import java.util.Date;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.event.entity.Event;

public abstract class AbstractEventDto<E extends Event>
        extends EntityDto<E, Long> {

    private static final long serialVersionUID = 1L;

    private String category;
    private int priority;

    private int flags;
    private int state;
    private boolean closed;

    private int statusId;
    private EventStatusDto status;

    private Long actorId;
    private UserDto actor;

    private String subject;
    private String message;
    private Date beginTime;
    private Date endTime;

    private long refId;
    private String refAlt;

    public AbstractEventDto() {
        super();
    }

    public AbstractEventDto(E source) {
        super(source);
    }

    @Override
    protected void _marshal(E source) {
        category = source.getCategory();
        priority = source.getPriority();
        state = source.getState();

        actorId = id(actor = new UserDto().marshal(source.getActor()));

        subject = source.getSubject();
        message = source.getMessage();
        beginTime = source.getBeginTime();
        endTime = source.getEndTime();

        refId = source.getRefId();
        refAlt = source.getRefAlt();
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, E target) {
        target.setCategory(category);
        target.setPriority(priority);
        target.setState(state);

        target.setSubject(subject);
        target.setMessage(message);
        target.setBeginTime(beginTime);
        target.setEndTime(endTime);

        unmarshal(context, target, Event.actorProperty, actorId, actor);

        target.setRefId(refId);
        target.setRefAlt(refAlt);
    }

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
        super.parse(map);

        category = map.getString("category");
        priority = map.getInt("priority");

        flags = map.getInt("flags");
        state = map.getInt("state");
        closed = map.getBoolean("closed");
        statusId = map.getInt("statusId");

        subject = map.getString("subject");
        message = map.getString("message");
        beginTime = map.getDate("beginTime");
        endTime = map.getDate("endTime");

        String _refId = map.getString("refId");
        if (_refId != null)
            refId = Long.parseLong(_refId);
        else
            refAlt = map.getString("refAlt");
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public EventStatusDto getStatus() {
        return status;
    }

    public void setStatus(EventStatusDto status) {
        this.status = status;
    }

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public UserDto getActor() {
        return actor;
    }

    public void setActor(UserDto actor) {
        this.actor = actor;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getRefId() {
        return refId;
    }

    public void setRefId(long refId) {
        this.refId = refId;
    }

    public String getRefAlt() {
        return refAlt;
    }

    public void setRefAlt(String refAlt) {
        this.refAlt = refAlt;
    }

}
