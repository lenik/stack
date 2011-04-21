package com.bee32.sem.event.web;

import java.util.Date;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.event.entity.Event;

public abstract class AbstractEventDto<E extends Event>
        extends EntityDto<E, Long> {

    private static final long serialVersionUID = 1L;

    private String category;
    private int priority;
    private String state;

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

        actor = new UserDto().marshal(source.getActor());

        subject = source.getSubject();
        message = source.getMessage();
        beginTime = source.getBeginTime();
        endTime = source.getEndTime();

        refId = source.getRefId();
        refAlt = source.getRefAlt();
    }

    @Override
    protected void _unmarshalTo(E target) {
        target.setCategory(category);
        target.setPriority(priority);
        target.setState(state);

        target.setActor(unmarshal(actor));

        target.setSubject(subject);
        target.setMessage(message);
        target.setBeginTime(beginTime);
        target.setEndTime(endTime);

        target.setRefId(refId);
        target.setRefAlt(refAlt);
    }

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
        super.parse(map);

        category = map.getString("category");
        priority = map.getInt("priority");
        state = map.getString("state");

        subject = map.getString("subject");
        message = map.getString("message");
        beginTime = map.getDate("beginTime");
        endTime = map.getDate("endTime");

        refId = map.getLong("refId");
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
