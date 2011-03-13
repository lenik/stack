package com.bee32.sem.event;

import java.util.Date;

import com.bee32.icsf.principal.IPrincipal;
import com.bee32.plover.orm.entity.EntityBean;

public class EnterpriseEvent
        extends EntityBean<Long>
        implements IEnterpriseEvent {

    private static final long serialVersionUID = 1L;

    private Class<?> sourceClass;
    private Object sourceKey;

    private Date time;
    private Date endTime;

    private IPrincipal actor;

    private String subject;
    private String message;

    public EnterpriseEvent() {
        super();
    }

    public EnterpriseEvent(String name) {
        super(name);
    }

    @Override
    public Class<?> getSourceClass() {
        return sourceClass;
    }

    public void setSourceClass(Class<?> sourceClass) {
        this.sourceClass = sourceClass;
    }

    @Override
    public Object getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(Object sourceKey) {
        this.sourceKey = sourceKey;
    }

    @Override
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public IPrincipal getActor() {
        return actor;
    }

    public void setActor(IPrincipal actor) {
        this.actor = actor;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
