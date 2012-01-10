package com.bee32.plover.orm.util;

import org.hibernate.EmptyInterceptor;

import com.bee32.plover.inject.ServiceTemplate;

/**
 * TODO how to install this into hibernate session?
 */
@ServiceTemplate
public abstract class AbstractEntityInterceptor
        extends EmptyInterceptor {

    private static final long serialVersionUID = 1L;

}
