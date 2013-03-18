package com.bee32.sem.calendar.entity;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.bee32.icsf.principal.User;
import com.bee32.plover.ox1.c.CEntityAuto;
import com.bee32.plover.ox1.color.Pink;
import com.bee32.sem.calendar.ICalendarEvent;

@Entity
@Pink
@SequenceGenerator(name = "idgen", sequenceName = "diary_seq", allocationSize = 1)
public class Diary
        extends CEntityAuto<Long>
        implements ICalendarEvent {

    private static final long serialVersionUID = 1L;

    User user;
    DiaryCategory category;

    String subject;
    String content;

    Date beginDate;
    Date endDate;

    int score;

    @Override
    public void populate(Object source) {
        if (source instanceof Diary)
            _populate((Diary) source);
        else
            super.populate(source);
    }

    protected void _populate(Diary o) {
        super._populate(o);
        user = o.user;
        category = o.category;
        subject = o.subject;
        content = o.content;
        beginDate = o.beginDate;
        endDate = o.endDate;
        score = o.score;
    }

    @ManyToOne(optional = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(optional = false)
    public DiaryCategory getCategory() {
        return category;
    }

    public void setCategory(DiaryCategory category) {
        this.category = category;
    }

    @Column(length = 80)
    @Override
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column(length = 30000)
    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    @Temporal(TemporalType.TIMESTAMP)
    public Date getBeginTime() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @Override
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndTime() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(nullable = false)
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Transient
    @Override
    public List<String> getStyles() {
        return Collections.emptyList();
    }

}
