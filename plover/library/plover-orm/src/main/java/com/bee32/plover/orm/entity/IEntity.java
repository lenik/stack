package com.bee32.plover.orm.entity;

import javax.ejb.EnterpriseBean;

/**
 * @see javax.ejb.EntityBean
 */
public interface IEntity<K>
        extends EnterpriseBean {

    K getPrimaryKey();

}
