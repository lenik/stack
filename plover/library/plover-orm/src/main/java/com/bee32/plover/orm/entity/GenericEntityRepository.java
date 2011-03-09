package com.bee32.plover.orm.entity;

import com.bee32.plover.inject.ComponentTemplate;

@ComponentTemplate
//@RepositoryTemplate
>>>>>>> 8052c3a163e83b911392fce15fcce58f9ba225aa
public abstract class GenericEntityRepository<E extends IEntity<K>, K>
        extends HibernateEntityRepository<E, K> {

    public GenericEntityRepository(Class<E> entityType, Class<K> keyType) {
        super(entityType, keyType);
    }

}
