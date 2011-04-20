package com.bee32.sem.event.entity;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.util.AliasUtil;

@Entity
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
public class EnterpriseEvent
        extends EntityBean<Long>
        implements IEnterpriseEvent {

    private static final long serialVersionUID = 1L;

    private String category;
    private Class<?> categoryClass;

    private long source;
    private String sourceAlt;

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

    @Column(length = 20)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        this.categoryClass = null;
    }

    @Transient
    @Override
    public Class<?> getCategoryClass() {
        if (categoryClass == null) {
            if (category == null)
                return null;
            else {
                categoryClass = AliasUtil.getAliasedType(category);
                if (categoryClass == null)
                    try {
                        // Entity-Alias? ...
                        categoryClass = Class.forName(category);
                    } catch (ClassNotFoundException e) {
                        categoryClass = null;
                    }
            }
        }
        return categoryClass;
    }

    public void setCategoryClass(Class<?> categoryClass) {
        this.categoryClass = categoryClass;
        this.category = categoryClass == null ? null : categoryClass.getName();
    }

    @Override
    public long getSource() {
        return source;
    }

    public void setSource(long source) {
        this.source = source;
    }

    @Column(length = 50)
    @Override
    public String getSourceAlt() {
        return sourceAlt;
    }

    public void setSourceAlt(String source) {
        this.sourceAlt = source;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
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
