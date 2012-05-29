package com.bee32.sem.calendar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.bee32.plover.ox1.dict.ShortNameDict;
import com.bee32.sem.calendar.DiaryVisibility;

@Entity
public class DiaryCategory
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    DiaryVisibility visibility = DiaryVisibility.PRIVATE;

    @Override
    public void populate(Object source) {
        if (source instanceof DiaryCategory)
            _populate((DiaryCategory) source);
        else
            super.populate(source);
    }

    protected void _populate(DiaryCategory o) {
        super._populate(o);
        visibility = o.visibility;
    }

    @Transient
    public DiaryVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(DiaryVisibility visibility) {
        if (visibility == null)
            throw new NullPointerException("visibility");
        this.visibility = visibility;
    }

    @Column(name = "visibility", nullable = false)
    char getVisibility_() {
        return visibility.getValue();
    }

    void setVisibility_(char visibility) {
        this.visibility = DiaryVisibility.forValue(visibility);
    }

}
