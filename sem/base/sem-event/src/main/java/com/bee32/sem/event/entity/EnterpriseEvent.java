package com.bee32.sem.event.entity;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.entity.EntityBean;

@Entity
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
public class EnterpriseEvent
        extends EntityBean<Long>
        implements IEnterpriseEvent {

    private static final long serialVersionUID = 1L;

    private Class<?> sourceClass;
    private String sourceKey;

    private Date beginTime;
    private Date endTime;

    private Principal actor;

    private String subject;
    private String message;

    public EnterpriseEvent() {
        super();
    }

    public EnterpriseEvent(String name) {
        super(name);
    }

    @Override
    public Class<?> getCategory() {
        return sourceClass;
    }

    public void setSourceClass(Class<?> sourceClass) {
        this.sourceClass = sourceClass;
    }

    @Override
    public String getSource() {
        return sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Override
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public Principal getActor() {
        return actor;
    }

    public void setActor(Principal actor) {
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
