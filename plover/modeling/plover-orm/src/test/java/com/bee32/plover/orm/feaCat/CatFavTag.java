package com.bee32.plover.orm.feaCat;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "cat_fav_seq", allocationSize = 1)
public class CatFavTag
        extends FavTag<Cat> {

    private static final long serialVersionUID = 1L;

    Integer id;

    @Override
    public void populate(Object source) {
        if (source instanceof CatFavTag)
            _populate((CatFavTag) source);
        else
            super.populate(source);
    }

    protected void _populate(CatFavTag o) {
        super._populate(o);
    }

}
