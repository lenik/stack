package com.bee32.zebra.oa.calendar;

import net.bodz.bas.meta.bean.DetailLevel;
import net.bodz.lily.entity.IdType;
import net.bodz.lily.model.base.schema.CategoryDef;
import net.bodz.lily.model.base.schema.PhaseDef;
import net.bodz.lily.model.base.security.User;
import net.bodz.lily.model.mixin.TagSet;
import net.bodz.lily.model.mx.base.CoMessage;

/**
 * 日记
 */
@IdType(Integer.class)
public class Diary
        extends CoMessage<Integer> {

    private static final long serialVersionUID = 1L;

    public Diary() {
    }

    @Override
    public void instantiate() {
        super.instantiate();
        setAccessMode(M_SHARED);
    }

    /**
     * 作者
     */
    @Override
    public User getOp() {
        return super.getOp();
    }

    @DetailLevel(DetailLevel.HIDDEN)
    @Override
    public CategoryDef getCategory() {
        return super.getCategory();
    }

    @DetailLevel(DetailLevel.HIDDEN)
    @Override
    public PhaseDef getPhase() {
        return super.getPhase();
    }

    @DetailLevel(DetailLevel.HIDDEN)
    @Override
    public TagSet getTags() {
        return super.getTags();
    }

}
