package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.criterion.Criterion;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.EnterpriseService;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityDto;

@Transactional(readOnly = true)
@ComponentTemplate
@Lazy
public abstract class EntityDso<E extends Entity<K>, K extends Serializable, //
/*        */Dto extends EntityDto<E, K>>
        extends EnterpriseService {

    protected final Class<E> entityType;
    protected final Class<K> keyType;
    protected final Class<Dto> transferType;

    protected final String entityTypeName;

    public EntityDso() {
        Type[] typeArgs = ClassUtil.getTypeArgs(getClass(), EntityDso.class);
        entityType = ClassUtil.bound1(typeArgs[0]);
        keyType = ClassUtil.bound1(typeArgs[1]);
        transferType = ClassUtil.bound1(typeArgs[2]);

        entityTypeName = ClassUtil.getDisplayName(entityType);
    }

    protected Class<E> getEntityType() {
        return entityType;
    }

    protected Class<K> getKeyType() {
        return keyType;
    }

    protected Class<Dto> getTransferType() {
        return transferType;
    }

    protected abstract EntityDao<E, K> getEntityDao();

    public Dto get(K key) {
        return DTOs.marshal(getTransferType(), getEntityDao().get(key));
    }

    public Dto get(int selection, K key) {
        return DTOs.marshal(getTransferType(), selection, getEntityDao().get(key));
    }

    public List<Dto> list() {
        List<E> entities = getEntityDao().list();
        return DTOs.marshalList(getTransferType(), entities);
    }

    public List<Dto> list(int selection) {
        List<E> entities = getEntityDao().list();
        return DTOs.marshalList(getTransferType(), selection, entities);
    }

    @Transactional
    public K save(Dto entityDto) {
        E entity = entityDto.unmarshal(this);
        return getEntityDao().save(entity);
    }

    @Transactional
    public void saveOrUpdateAll(Collection<? extends Dto> entityDtos) {
        EntityDao<E, K> entityDao = getEntityDao();
        for (Dto entityDto : entityDtos) {
            E entity = entityDto.unmarshal(this);
            entityDao.saveOrUpdate(entity);
        }
    }

    @Transactional
    public void update(Dto entityDto) {
        E entity = entityDto.unmarshal(this);
        getEntityDao().update(entity);
    }

    @Transactional
    public void update(Dto entityDto, LockMode lockMode)
            throws DataAccessException {
        E entity = entityDto.unmarshal(this);
        getEntityDao().update(entity, lockMode);
    }

    @Transactional
    public void saveOrUpdate(Dto entityDto) {
        E entity = entityDto.unmarshal(this);
        getEntityDao().saveOrUpdate(entity);
    }

    @Transactional
    public void delete(Dto entityDto) {
        E entity = entityDto.unmarshal(this);
        getEntityDao().delete(entity);
    }

    @Transactional
    public void delete(Dto entityDto, LockMode lockMode)
            throws DataAccessException {
        E entity = entityDto.unmarshal(this);
        getEntityDao().delete(entity, lockMode);
    }

    @Transactional
    public void deleteByKey(K key) {
        getEntityDao().deleteByKey(key);
    }

    @Transactional
    public void deleteAll() {
        getEntityDao().deleteAll();
    }

    @Transactional
    public void merge(E entity)
            throws DataAccessException {
        getEntityDao().merge(entity);
    }

    public long count() {
        return getEntityDao().count();
    }

    public long count(Criterion... restrictions) {
        return getEntityDao().count(restrictions);
    }

}
