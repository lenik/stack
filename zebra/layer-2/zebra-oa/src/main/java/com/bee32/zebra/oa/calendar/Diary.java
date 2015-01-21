package com.bee32.zebra.oa.calendar;

import java.util.List;

import net.bodz.bas.meta.bean.DetailLevel;

import com.tinylily.model.base.IdType;
import com.tinylily.model.base.schema.CategoryDef;
import com.tinylily.model.base.schema.PhaseDef;
import com.tinylily.model.base.schema.TagDef;
import com.tinylily.model.base.security.User;
import com.tinylily.model.mx.base.CoMessage;

@IdType(Integer.class)
public class Diary
        extends CoMessage<Integer> {

    private static final long serialVersionUID = 1L;

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
    public List<TagDef> getTags() {
        return super.getTags();
    }

}
