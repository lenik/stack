package com.bee32.zebra.oa.calendar;

import java.util.HashSet;
import java.util.Set;

import net.bodz.bas.meta.bean.DetailLevel;
import net.bodz.bas.repr.form.meta.OfGroup;
import net.bodz.bas.repr.form.meta.StdGroup;
import net.bodz.lily.entity.IdType;
import net.bodz.lily.model.base.security.User;
import net.bodz.lily.model.mx.CoMessage;

/**
 * 日记
 */
@IdType(Integer.class)
public class Diary
        extends CoMessage<Integer> {

    private static final long serialVersionUID = 1L;

    DiaryCategory category;
    DiaryPhase phase;
    Set<DiaryTag> tags;

    public Diary() {
    }

    @Override
    public void instantiate() {
        super.instantiate();
        setAccessMode(M_SHARED);
        this.tags = new HashSet<>();
    }

    /**
     * 作者
     */
    @Override
    public User getOp() {
        return super.getOp();
    }

    /**
     * @label Category
     * @label.zh 分类
     */
    @OfGroup(StdGroup.Classification.class)
    public DiaryCategory getCategory() {
        return category;
    }

    public void setCategory(DiaryCategory category) {
        this.category = category;
    }

    public DiaryPhase getPhase() {
        return phase;
    }

    public void setPhase(DiaryPhase phase) {
        this.phase = phase;
    }

    @DetailLevel(DetailLevel.HIDDEN)
    public Set<DiaryTag> getTags() {
        return tags;
    }

    public void setTags(Set<DiaryTag> tags) {
        this.tags = tags;
    }

}
