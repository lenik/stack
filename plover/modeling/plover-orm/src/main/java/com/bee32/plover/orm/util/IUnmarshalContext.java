package com.bee32.plover.orm.util;

import java.io.Serializable;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.EntityDao;

public interface IUnmarshalContext {

    <Dao extends EntityDao<E, K>, E extends EntityBean<K>, K extends Serializable> //
    Dao getDao(Class<E> entityType);

}
