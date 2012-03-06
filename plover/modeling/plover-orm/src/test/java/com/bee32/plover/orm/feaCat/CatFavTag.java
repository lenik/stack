package com.bee32.plover.orm.feaCat;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "cat_fav_seq", allocationSize = 1)
public class CatFavTag
        extends FavTag<Cat> {

    private static final long serialVersionUID = 1L;

    Integer id;

X-Population

}
