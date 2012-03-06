package com.bee32.sem.calendar.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.icsf.principal.User;
import com.bee32.plover.ox1.c.CEntityAuto;
import com.bee32.plover.ox1.color.Yellow;

@Entity
@Yellow
@SequenceGenerator(name = "idgen", sequenceName = "diary_observer_seq", allocationSize = 1)
public class DiaryObserver
        extends CEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    User user;
    DiaryCategory category;
    User observer;

    @Override
    public void populate(Object source) {
        if (source instanceof DiaryObserver)
            _populate((DiaryObserver) source);
        else
            super.populate(source);
    }

    protected void _populate(DiaryObserver o) {
        super._populate(o);
        user = o.user;
        category = o.category;
        observer = o.observer;
    }

    @NaturalId
    @ManyToOne(optional = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user == null)
            throw new NullPointerException("user");
        this.user = user;
    }

    @NaturalId
    @ManyToOne(optional = false)
    public DiaryCategory getCategory() {
        return category;
    }

    public void setCategory(DiaryCategory category) {
        if (category == null)
            throw new NullPointerException("category");
        this.category = category;
    }

    @ManyToOne(optional = false)
    public User getObserver() {
        return observer;
    }

    public void setObserver(User observer) {
        if (observer == null)
            throw new NullPointerException("observer");
        this.observer = observer;
    }

}
