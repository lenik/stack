package com.bee32.plover.ox1.typePref;

import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.plover.orm.util.ITypeAbbrAware;

public class TypePrefDao<E extends TypePrefEntity>
        extends EntityDao<E, String>
        implements ITypeAbbrAware {

    public E get(Class<?> clazz) {
        return get(ABBR.abbr(clazz));
    }

    // public E fetch(Class<?> clazz) {
    // return fetch(ABBR.abbr(clazz));
    // }

    public E load(Class<?> clazz) {
        return getOrFail(ABBR.abbr(clazz));
    }

    public void deleteByKey(Class<?> clazz) {
        deleteByKey(ABBR.abbr(clazz));
    }

}
