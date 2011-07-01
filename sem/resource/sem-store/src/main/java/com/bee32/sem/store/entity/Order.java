package com.bee32.sem.store.entity;

import javax.persistence.MappedSuperclass;

import com.bee32.sem.base.tx.TxEntity;

@MappedSuperclass
public class Order<T extends Thing<?>>
        extends TxEntity {

    private static final long serialVersionUID = 1L;

}
